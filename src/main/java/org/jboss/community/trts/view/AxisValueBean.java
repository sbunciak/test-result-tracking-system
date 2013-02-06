package org.jboss.community.trts.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.community.trts.model.AxisValue;
import org.jboss.community.trts.model.Axis;

/**
 * Backing bean for AxisValue entities.
 * <p>
 * This class provides CRUD functionality for all AxisValue entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AxisValueBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving AxisValue entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private AxisValue axisValue;

   public AxisValue getAxisValue()
   {
      return this.axisValue;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.axisValue = this.search;
      }
      else
      {
         this.axisValue = this.entityManager.find(AxisValue.class, getId());
      }
   }

   /*
    * Support updating and deleting AxisValue entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.axisValue);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.axisValue);
            return "view?faces-redirect=true&id=" + this.axisValue.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         this.entityManager.remove(this.entityManager.find(AxisValue.class, getId()));
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching AxisValue entities with pagination
    */

   private int page;
   private long count;
   private List<AxisValue> pageItems;

   private AxisValue search = new AxisValue();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public AxisValue getSearch()
   {
      return this.search;
   }

   public void setSearch(AxisValue search)
   {
      this.search = search;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<AxisValue> root = countCriteria.from(AxisValue.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<AxisValue> criteria = builder.createQuery(AxisValue.class);
      root = criteria.from(AxisValue.class);
      TypedQuery<AxisValue> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<AxisValue> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      Axis axis = this.search.getAxis();
      if (axis != null)
      {
         predicatesList.add(builder.equal(root.get("axis"), axis));
      }
      String value = this.search.getValue();
      if (value != null && !"".equals(value))
      {
         predicatesList.add(builder.like(root.<String> get("value"), '%' + value + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<AxisValue> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back AxisValue entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<AxisValue> getAll()
   {

      CriteriaQuery<AxisValue> criteria = this.entityManager.getCriteriaBuilder().createQuery(AxisValue.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(AxisValue.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return AxisValueBean.this.entityManager.find(AxisValue.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((AxisValue) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private AxisValue add = new AxisValue();

   public AxisValue getAdd()
   {
      return this.add;
   }

   public AxisValue getAdded()
   {
      AxisValue added = this.add;
      this.add = new AxisValue();
      return added;
   }
}