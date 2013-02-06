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

import org.jboss.community.trts.model.AxisConfig;
import org.jboss.community.trts.model.AxisPriority;
import org.jboss.community.trts.model.ProductVersion;

/**
 * Backing bean for AxisConfig entities.
 * <p>
 * This class provides CRUD functionality for all AxisConfig entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class AxisConfigBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving AxisConfig entities
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

   private AxisConfig axisConfig;

   public AxisConfig getAxisConfig()
   {
      return this.axisConfig;
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
         this.axisConfig = this.search;
      }
      else
      {
         this.axisConfig = this.entityManager.find(AxisConfig.class, getId());
      }
   }

   /*
    * Support updating and deleting AxisConfig entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.axisConfig);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.axisConfig);
            return "view?faces-redirect=true&id=" + this.axisConfig.getId();
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
         this.entityManager.remove(this.entityManager.find(AxisConfig.class, getId()));
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
    * Support searching AxisConfig entities with pagination
    */

   private int page;
   private long count;
   private List<AxisConfig> pageItems;

   private AxisConfig search = new AxisConfig();

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

   public AxisConfig getSearch()
   {
      return this.search;
   }

   public void setSearch(AxisConfig search)
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
      Root<AxisConfig> root = countCriteria.from(AxisConfig.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<AxisConfig> criteria = builder.createQuery(AxisConfig.class);
      root = criteria.from(AxisConfig.class);
      TypedQuery<AxisConfig> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<AxisConfig> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

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

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<AxisConfig> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back AxisConfig entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<AxisConfig> getAll()
   {

      CriteriaQuery<AxisConfig> criteria = this.entityManager.getCriteriaBuilder().createQuery(AxisConfig.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(AxisConfig.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return AxisConfigBean.this.entityManager.find(AxisConfig.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((AxisConfig) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private AxisConfig add = new AxisConfig();

   public AxisConfig getAdd()
   {
      return this.add;
   }

   public AxisConfig getAdded()
   {
      AxisConfig added = this.add;
      this.add = new AxisConfig();
      return added;
   }
}