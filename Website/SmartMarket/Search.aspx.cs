using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Search : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        String keyword = Request.QueryString["keyword"];
        String developerID = Request.QueryString["developer"];
        String categoryID = Request.QueryString["category"];
        String newest = Request.QueryString["newest"];
        String suggested = Request.QueryString["suggested"];
        if (keyword != null)
        {
            apps_LinqDataSource.Where = "published == true && (name.Contains(\"" + keyword + "\") || developer.name.Contains(\"" + keyword + "\") || description.Contains(\"" + keyword + "\"))";
        }
        else if (developerID != null)
        {
            int id = 0;
            if (Int32.TryParse(developerID, out id))
            {
                apps_LinqDataSource.Where = "published == true && developerID.Equals(\"" + developerID + "\")";
            }
            else
            {
                String info = Strings.urlNotFound;
                Response.Redirect("Info.aspx?info=" + info);
            }
        }
        else if (categoryID != null)
        {              
            int id = 0;
            if (Int32.TryParse(categoryID, out id))
            {
                apps_LinqDataSource.Where = "published == true && categoryID.Equals(\"" + categoryID + "\")";
            }
            else
            {
                String info = Strings.urlNotFound;
                Response.Redirect("Info.aspx?info=" + info);
            }
        }
        else if (newest != null)
        {
            apps_LinqDataSource.Where = "published == true";
            apps_LinqDataSource.OrderBy = "appID desc";
        }
        else if (suggested != null)
        {
            String loginEmail = HttpContext.Current.User.Identity.Name;
            Boolean loggedIn = HttpContext.Current.User.Identity.IsAuthenticated;
            if (!loggedIn)
            {
                String info = Strings.accessDenied;
                Response.Redirect("Info.aspx?info=" + info);
            }
            else
            {
                SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
                User user = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
                apps_GridView.DataSourceID = null;
                apps_GridView.DataSource = GetSuggestedApps(db, user, 15);
                apps_GridView.DataBind();
            }
        }
        else
        {
            apps_LinqDataSource.Where = "published == true";
        }
    }


    private List<App> GetSuggestedApps(SmartMarketDataClassesDataContext db, User user, int limit)
    {
        List<App> result = null;
        result = (from r in user.Recommendations
                  where r.App.published && r.App.UserApps.SingleOrDefault(u => u.userID.Equals(user.userID)) == null
                  orderby r.expectedRate descending
                  select r.App).Take(limit).ToList();
        if (result == null || result.Count == 0)
        {
            result = (from a in db.Apps
                      where a.published && a.UserApps.SingleOrDefault(u => u.userID.Equals(user.userID)) == null
                      orderby a.installs descending
                      select a).Take(limit).ToList();
        }
        return result;
    }
   
}