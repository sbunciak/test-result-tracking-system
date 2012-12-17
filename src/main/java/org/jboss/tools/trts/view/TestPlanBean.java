package org.jboss.tools.trts.view;

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

import org.jboss.community.trts.model.TestPlan;
import org.jboss.community.trts.model.ProductVersion;
import org.jboss.community.trts.model.TestType;

/**
 * Backing bean for TestPlan entities.
 * <p>
 * This class provides CRUD functionality for all TestPlan entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TestPlanBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving TestPlan entities
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

   private TestPlan testPlan;

   public TestPlan getTestPlan()
   {
      return this.testPlan;
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
         this.testPlan = this.search;
      }
      else
      {
         this.testPlan = this.entityManager.find(TestPlan.class, getId());
      }
   }

   /*
    * Support updating and deleting TestPlan entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.testPlan);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.testPlan);
            return "view?faces-redirect=true&id=" + this.testPlan.getId();
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
         this.entityManager.remove(this.entityManager.find(TestPlan.class, getId()));
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
    * Support searching TestPlan entities with pagination
    */

   private int page;
   private long count;
   private List<TestPlan> pageItems;

   private TestPlan search = new TestPlan();

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

   public TestPlan getSearch()
   {
      return this.search;
   }

   public void setSearch(TestPlan search)
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
      Root<TestPlan> root = countCriteria.from(TestPlan.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<TestPlan> criteria = builder.createQuery(TestPlan.class);
      root = criteria.from(TestPlan.class);
      TypedQuery<TestPlan> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<TestPlan> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      ProductVersion productVersion = this.search.getProductVersion();
      if (productVersion != null)
      {
         predicatesList.add(builder.equal(root.get("productVersion"), productVersion));
      }
      String rules = this.search.getRules();
      if (rules != null && !"".equals(rules))
      {
         predicatesList.add(builder.like(root.<String> get("rules"), '%' + rules + '%'));
      }
      String name = this.search.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(root.<String> get("name"), '%' + name + '%'));
      }
      String description = this.search.getDescription();
      if (description != null && !"".equals(description))
      {
         predicatesList.add(builder.like(root.<String> get("description"), '%' + description + '%'));
      }
      TestType type = this.search.getType();
      if (type != null)
      {
         predicatesList.add(builder.equal(root.get("type"), type));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<TestPlan> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back TestPlan entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<TestPlan> getAll()
   {

      CriteriaQuery<TestPlan> criteria = this.entityManager.getCriteriaBuilder().createQuery(TestPlan.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(TestPlan.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return TestPlanBean.this.entityManager.find(TestPlan.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((TestPlan) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private TestPlan add = new TestPlan();

   public TestPlan getAdd()
   {
      return this.add;
   }

   public TestPlan getAdded()
   {
      TestPlan added = this.add;
      this.add = new TestPlan();
      return added;
   }
}