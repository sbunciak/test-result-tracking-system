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

import org.jboss.community.trts.model.TestCase;
import org.jboss.community.trts.model.TestPlan;

/**
 * Backing bean for TestCase entities.
 * <p>
 * This class provides CRUD functionality for all TestCase entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TestCaseBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving TestCase entities
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

   private TestCase testCase;

   public TestCase getTestCase()
   {
      return this.testCase;
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
         this.testCase = this.search;
      }
      else
      {
         this.testCase = this.entityManager.find(TestCase.class, getId());
      }
   }

   /*
    * Support updating and deleting TestCase entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.testCase);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.testCase);
            return "view?faces-redirect=true&id=" + this.testCase.getId();
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
         this.entityManager.remove(this.entityManager.find(TestCase.class, getId()));
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
    * Support searching TestCase entities with pagination
    */

   private int page;
   private long count;
   private List<TestCase> pageItems;

   private TestCase search = new TestCase();

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

   public TestCase getSearch()
   {
      return this.search;
   }

   public void setSearch(TestCase search)
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
      Root<TestCase> root = countCriteria.from(TestCase.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<TestCase> criteria = builder.createQuery(TestCase.class);
      root = criteria.from(TestCase.class);
      TypedQuery<TestCase> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<TestCase> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      TestPlan testPlan = this.search.getTestPlan();
      if (testPlan != null)
      {
         predicatesList.add(builder.equal(root.get("testPlan"), testPlan));
      }
      String defaultTester = this.search.getDefaultTester();
      if (defaultTester != null && !"".equals(defaultTester))
      {
         predicatesList.add(builder.like(root.<String> get("defaultTester"), '%' + defaultTester + '%'));
      }
      String ciLink = this.search.getCiLink();
      if (ciLink != null && !"".equals(ciLink))
      {
         predicatesList.add(builder.like(root.<String> get("ciLink"), '%' + ciLink + '%'));
      }
      String title = this.search.getTitle();
      if (title != null && !"".equals(title))
      {
         predicatesList.add(builder.like(root.<String> get("title"), '%' + title + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<TestCase> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back TestCase entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<TestCase> getAll()
   {

      CriteriaQuery<TestCase> criteria = this.entityManager.getCriteriaBuilder().createQuery(TestCase.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(TestCase.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return TestCaseBean.this.entityManager.find(TestCase.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((TestCase) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private TestCase add = new TestCase();

   public TestCase getAdd()
   {
      return this.add;
   }

   public TestCase getAdded()
   {
      TestCase added = this.add;
      this.add = new TestCase();
      return added;
   }
}