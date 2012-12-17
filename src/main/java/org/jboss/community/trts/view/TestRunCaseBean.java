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

import org.jboss.community.trts.model.TestRunCase;
import org.jboss.community.trts.model.TestResult;
import org.jboss.community.trts.model.TestRun;

/**
 * Backing bean for TestRunCase entities.
 * <p>
 * This class provides CRUD functionality for all TestRunCase entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TestRunCaseBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving TestRunCase entities
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

   private TestRunCase testRunCase;

   public TestRunCase getTestRunCase()
   {
      return this.testRunCase;
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
         this.testRunCase = this.search;
      }
      else
      {
         this.testRunCase = this.entityManager.find(TestRunCase.class, getId());
      }
   }

   /*
    * Support updating and deleting TestRunCase entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.testRunCase);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.testRunCase);
            return "view?faces-redirect=true&id=" + this.testRunCase.getId();
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
         this.entityManager.remove(this.entityManager.find(TestRunCase.class, getId()));
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
    * Support searching TestRunCase entities with pagination
    */

   private int page;
   private long count;
   private List<TestRunCase> pageItems;

   private TestRunCase search = new TestRunCase();

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

   public TestRunCase getSearch()
   {
      return this.search;
   }

   public void setSearch(TestRunCase search)
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
      Root<TestRunCase> root = countCriteria.from(TestRunCase.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<TestRunCase> criteria = builder.createQuery(TestRunCase.class);
      root = criteria.from(TestRunCase.class);
      TypedQuery<TestRunCase> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<TestRunCase> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      TestRun testRun = this.search.getTestRun();
      if (testRun != null)
      {
         predicatesList.add(builder.equal(root.get("testRun"), testRun));
      }
      TestResult result = this.search.getResult();
      if (result != null)
      {
         predicatesList.add(builder.equal(root.get("result"), result));
      }
      String bugTrLink = this.search.getBugTrLink();
      if (bugTrLink != null && !"".equals(bugTrLink))
      {
         predicatesList.add(builder.like(root.<String> get("bugTrLink"), '%' + bugTrLink + '%'));
      }
      String assignee = this.search.getAssignee();
      if (assignee != null && !"".equals(assignee))
      {
         predicatesList.add(builder.like(root.<String> get("assignee"), '%' + assignee + '%'));
      }
      String ciLink = this.search.getCiLink();
      if (ciLink != null && !"".equals(ciLink))
      {
         predicatesList.add(builder.like(root.<String> get("ciLink"), '%' + ciLink + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<TestRunCase> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back TestRunCase entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<TestRunCase> getAll()
   {

      CriteriaQuery<TestRunCase> criteria = this.entityManager.getCriteriaBuilder().createQuery(TestRunCase.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(TestRunCase.class))).getResultList();
   }

   public Converter getConverter()
   {

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return TestRunCaseBean.this.entityManager.find(TestRunCase.class, Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((TestRunCase) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private TestRunCase add = new TestRunCase();

   public TestRunCase getAdd()
   {
      return this.add;
   }

   public TestRunCase getAdded()
   {
      TestRunCase added = this.add;
      this.add = new TestRunCase();
      return added;
   }
}