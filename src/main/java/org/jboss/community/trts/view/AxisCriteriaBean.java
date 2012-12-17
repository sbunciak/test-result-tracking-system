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

import org.jboss.community.trts.model.AxisCriteria;
import org.jboss.community.trts.model.Axis;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.ProductVersion;

/**
 * Backing bean for AxisCriteria entities.
 * <p>
 * This class provides CRUD functionality for all AxisCriteria entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AxisCriteriaBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving AxisCriteria entities
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

   private AxisCriteria axisCriteria;

   public AxisCriteria getAxisCriteria()
   {
      return this.axisCriteria;
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
         this.axisCriteria = this.search;
      }
      else
      {
         this.axisCriteria = this.entityManager.find(AxisCriteria.class, getId());
      }
   }

   /*
    * Support updating and deleting AxisCriteria entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.axisCriteria);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.axisCriteria);
            return "view?faces-redirect=true&id=" + this.axisCriteria.getId();
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
         this.entityManager.remove(this.entityManager.find(AxisCriteria.class, getId()));
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
    * Support searching AxisCriteria entities with pagination
    */

   private int page;
   private long count;
   private List<AxisCriteria> pageItems;

   private AxisCriteria search = new AxisCriteria();

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

   public AxisCriteria getSearch()
   {
      return this.search;
   }

   public void setSearch(AxisCriteria search)
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
      Root<AxisCriteria> root = countCriteria.from(AxisCriteria.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<AxisCriteria> criteria = builder.createQuery(AxisCriteria.class);
      root = criteria.from(AxisCriteria.class);
      TypedQuery<AxisCriteria> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<AxisCriteria> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      Axis axis = this.search.getAxis();
      if (axis != null)
      {
         predicatesList.add(builder.equal(root.get("axis"), axis));
      }
      ProductVersion productVersion = this.search.getProductVersion();
      if (productVersion != null)
      {
         predicatesList.add(builder.equal(root.get("productVersion"), productVersion));
      }
      AxisPriority priority = this.search.getPriority();
      if (priority != null)
      {
         predicatesList.add(builder.equal(root.get("priority"), priority));
      }
      String value = this.search.getValue();
      if (value != null && !"".equals(value))
      {
         predicatesList.add(builder.like(root.<String> get("value"), '%' + value + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<AxisCriteria> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back AxisCriteria entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<AxisCriteria> getAll()
   {

      CriteriaQuery<AxisCriteria> criteria = this.entityManager.getCriteriaBuilder().createQuery(AxisCriteria.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(AxisCriteria.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return AxisCriteriaBean.this.entityManager.find(AxisCriteria.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((AxisCriteria) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private AxisCriteria add = new AxisCriteria();

   public AxisCriteria getAdd()
   {
      return this.add;
   }

   public AxisCriteria getAdded()
   {
      AxisCriteria added = this.add;
      this.add = new AxisCriteria();
      return added;
   }
}