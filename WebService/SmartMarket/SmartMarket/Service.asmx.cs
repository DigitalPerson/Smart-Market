using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data.SqlClient;
using System.Web.Mail;

namespace SmartMarket
{
    /// <summary>
    /// Summary description for Service1
    /// </summary>
    [WebService(Namespace = "com.digitalsoft.smartmarket")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class Service : System.Web.Services.WebService
    {
        [WebMethod]
        public List<AppType> GetAppTypes()
        {
            IEnumerable<AppType> result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = from at in db.AppTypes
                     orderby at.name ascending
                     select at;
            return result.ToList();
        }
        [WebMethod]
        public List<Category> GetCategories(String appTypeName)
        {
            IEnumerable<Category> result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = from c in db.Categories
                     where c.AppType.name.Equals(appTypeName)
                     orderby c.name ascending
                     select c;
            return result.ToList();
        }
        [WebMethod]
        public List<Country> GetCountries()
        {
            IEnumerable<Country> result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = from c in db.Countries
                     orderby c.name ascending
                     select c;
            return result.ToList();
        }
        [WebMethod]
        public App GetApp(String packageName)
        {
            App result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = db.Apps.SingleOrDefault(a => a.published && a.packageName.Equals(packageName));
            return result;
        }
        [WebMethod]
        public App CheckForNewerVersion(String packageName, String installedVersion)
        {
            App result = null;
            VersionComprator cmp = new VersionComprator();
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            App app = db.Apps.SingleOrDefault(a => a.published && a.packageName.Equals(packageName));
            if (app != null && cmp.compare(app.Version.versionNumber, installedVersion) > 0)
            {
                result = app;
            }
            return result;
        }
        [WebMethod]
        public App CheckForOlderVersion(String packageName, String installedVersion)
        {
            App result = null;
            VersionComprator cmp = new VersionComprator();
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            App app = db.Apps.SingleOrDefault(a => a.published && a.packageName.Equals(packageName));
            if (app != null && cmp.compare(app.Version.versionNumber, installedVersion) < 0)
            {
                result = app;
            }
            return result;
        }
        [WebMethod]
        public UserApp DownloadApp(String email, String packageName)
        {
            UserApp userApp = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            App app = db.Apps.SingleOrDefault(a => a.packageName.Equals(packageName));
            User user = db.Users.SingleOrDefault(u => u.email.ToLower().Equals(email.ToLower()));
            if (user == null)
            {
                return null;
            }
            app.installs++;
            userApp = app.UserApps.SingleOrDefault(ua => ua.User.email.ToLower().Equals(email.ToLower()));
            if (userApp == null)
            {
                userApp = new UserApp();
                userApp.User = user; 
                userApp.App = app;
                userApp.rate = 0;
                userApp.favorite = false;
                db.UserApps.InsertOnSubmit(userApp);
            }
            db.SubmitChanges();
            return userApp;
        }
        [WebMethod]
        public List<App> SearchForApp(String keyword, String startIndex, String limit)
        {
            IEnumerable<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = (from a in db.Apps
                     where a.published && a.name.Contains(keyword) || a.Developer.name.Contains(keyword) || a.description.Contains(keyword)
                     orderby a.installs descending
                     select a).Skip(startIndexInt).Take(limitInt);
            return result.ToList();
        }
        [WebMethod]
        public List<App> GetSuggestedApps(String email, String startIndex, String limit)
        {
            List<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            User user = db.Users.SingleOrDefault(u => u.email.Equals(email));
            result = (from r in user.Recommendations
                      where r.App.published && r.App.UserApps.SingleOrDefault(u => u.userID.Equals(user.userID)) == null
                      orderby r.expectedRate descending
                      select r.App).Skip(startIndexInt).Take(limitInt).ToList();
            if (result.Count == 0)
            {
                result = (from a in db.Apps
                          where a.published && a.UserApps.SingleOrDefault(u => u.userID.Equals(user.userID)) == null
                          orderby a.installs descending
                          select a).Skip(startIndexInt).Take(limitInt).ToList();
            }
            return result;
        }
        [WebMethod]
        public List<App> GetFeaturedApps(String startIndex, String limit)
        {
            List<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = (from a in db.Apps
                      where a.published
                      orderby a.installs descending
                      select a).Skip(startIndexInt).Take(limitInt).ToList();
            return result;
        }
        [WebMethod]
        public List<App> GetApps(String appTypeName, String categoryName, String startIndex, String limit)
        {
            IEnumerable<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = (from a in db.Apps
                     where a.published && a.Category.AppType.name.Equals(appTypeName) && a.Category.name.Equals(categoryName)
                     orderby a.installs descending
                     select a).Skip(startIndexInt).Take(limitInt);
            return result.ToList();
        }
        [WebMethod]
        public List<App> GetNewestApps(String startIndex, String limit)
        {
            IEnumerable<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = (from a in db.Apps
                      where a.published
                      orderby a.appID descending
                      select a).Skip(startIndexInt).Take(limitInt);
            return result.ToList();
        }
        [WebMethod]
        public List<App> GetAppsForSpecificDeveloper(String developerID, String startIndex, String limit)
        {
            IEnumerable<App> result = null;
            int limitInt = Int32.Parse(limit);
            int startIndexInt = Int32.Parse(startIndex);
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = (from a in db.Apps
                     where a.published && a.developerID.Equals(Int32.Parse(developerID))
                     orderby a.installs descending
                     select a).Skip(startIndexInt).Take(limitInt);
            return result.ToList();
        }
        [WebMethod]
        public UserApp SetRateForApp(String email, String packageName, String rate)
        {
            UserApp userApp = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            App app = db.Apps.SingleOrDefault(a => a.packageName.Equals(packageName));
            User user = db.Users.SingleOrDefault(u => u.email.ToLower().Equals(email.ToLower())); ;
            if (app == null || user == null)
            {
                return null;
            }
            userApp = app.UserApps.SingleOrDefault(ua => ua.User.email.ToLower().Equals(email.ToLower()));
            if (userApp == null)
            {
                userApp = new UserApp();
                userApp.User = user;
                userApp.App = app;
                userApp.rate = float.Parse(rate);
                userApp.favorite = false;
                db.UserApps.InsertOnSubmit(userApp);
            }
            else
            {
                userApp.rate = float.Parse(rate);
            }
            var rates = from ua in app.UserApps
                        where !ua.rate.Equals(0) && !ua.rate.Equals(null)
                        select ua.rate;
            app.rate = rates.Average();
            app.ratesCount = rates.Count();
            db.SubmitChanges();
            return userApp;
        }
        [WebMethod]
        public UserApp GetUserApp(String email, String packageName)
        {
            UserApp result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = db.UserApps.SingleOrDefault(ua => ua.User.email.ToLower().Equals(email.ToLower()) && ua.App.packageName.Equals(packageName));
            return result;
        }
        [WebMethod]
        public Boolean AddUser(String firstName, String lastName, String gender, String countryName, String email, String password)
        {
            Boolean result = true; ;
            using (SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext())
            {
                User user = new User();
                user.firstName = firstName;
                user.lastName = lastName;
                user.gender = gender;
                user.Country = db.Countries.Single(c => c.name.Equals(countryName));
                user.email = email;
                user.password = password;
                db.Users.InsertOnSubmit(user);
                try
                {
                    db.SubmitChanges();
                }
                catch (SqlException ex)
                {
                    result = false;
                } 
            }
            return result;
        }
        [WebMethod]
        public User GetUser(String email)
        {
            User result = null;
            SmartMarketDataClassesDataContext db = new SmartMarketDataClassesDataContext();
            result = db.Users.SingleOrDefault(u => u.email.ToLower().Equals(email.ToLower()));
            return result;
        }  
    }
}