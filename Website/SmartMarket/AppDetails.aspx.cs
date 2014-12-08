using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using AjaxControlToolkit;
using System.Data.SqlClient;

public partial class AppDetails : System.Web.UI.Page
{
    private SmartMarketDataClassesDataContext db;
    private String packageName;
    private App app;
    private UserApp userApp;
    private String appsPathNotMapped;
    private String appsPath;
    private String iconsPathNotMapped;
    private String iconsPath;
    private String screenshotsPathNotMapped;
    private String screenshotsPath;
    private String qrCodesPathNotMapped;
    private String loginEmail;
    private Boolean loggedIn;
    protected void Page_Load(object sender, EventArgs e)
    {
        appsPathNotMapped = Config.appsPathNotMapped;
        appsPath = Server.MapPath(appsPathNotMapped);
        iconsPathNotMapped = Config.iconsPathNotMapped;
        iconsPath = Server.MapPath(iconsPathNotMapped);
        screenshotsPathNotMapped = Config.screenshotsPathNotMapped;
        screenshotsPath = Server.MapPath(screenshotsPathNotMapped);
        qrCodesPathNotMapped = Config.qrCodesPathNotMapped;
        packageName = Request.QueryString["package"];
        loginEmail = HttpContext.Current.User.Identity.Name;
        loggedIn = HttpContext.Current.User.Identity.IsAuthenticated;
        if (packageName == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        db = new SmartMarketDataClassesDataContext();
        app = db.Apps.SingleOrDefault(a => a.packageName.Equals(packageName) && a.published);
        if (app == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        userApp = app.UserApps.SingleOrDefault(ua => ua.User.email.Equals(loginEmail));
        ShowAppDetails();
    }
    private void ShowAppDetails()
    {
        icon_Image.ImageUrl = iconsPathNotMapped + app.appID + ".png";
        name_Label.Text = app.name;
        developer_HyperLink.Text = app.Developer.name;
        developer_HyperLink.NavigateUrl = "Search.aspx?developer=" + app.developerID;
        description_Label.Text = app.description;
        developerWebsite_HyperLink.Text = app.Developer.website;
        developerWebsite_HyperLink.NavigateUrl = app.Developer.website;
        developerEmail_HyperLink.Text = app.Developer.supportEmail;
        developerEmail_HyperLink.NavigateUrl = "mailto:" + app.Developer.supportEmail;
        if (userApp != null)
        {
            yourRate_rating.CurrentRating = Convert.ToInt32(Math.Round((Double)userApp.rate));
        }
        else
        {
            yourRate_rating.CurrentRating = 0;
        }       
        averageRate_rating.CurrentRating = Convert.ToInt32(Math.Round((Double)app.rate));
        ratesCount_Label.Text = app.ratesCount.ToString();
        date_Label.Text = app.Version.date.ToLongDateString();
        versionNumber_Label.Text = app.Version.versionNumber;
        minAndroidVersion_Label.Text = app.Version.minAndroidVersion;
        Category_Label.Text = app.Category.name;
        installs_Label.Text = app.installs.ToString();
        size_Label.Text = app.Version.size.ToString();
        qrCode_Image.ImageUrl = qrCodesPathNotMapped + app.appID + ".png";     
    }
    protected void download_Button_Click(object sender, EventArgs e)
    {
        if (loggedIn)
        {
            DownloadApp();
            Helper.DownloadFile(appsPath + app.Version.versionID + ".apk", app.name + "_" + app.Version.versionNumber + ".apk", "application/vnd.android.package-archive");
        }
        else
        {
            MessageBox.Show(Strings.loginFirst);
        }
    }
    protected void alternativeDownloadMethod_LinkButton_Click(object sender, EventArgs e)
    {
        if (loggedIn)
        {
            DownloadApp();
            Response.Redirect(appsPathNotMapped + app.Version.versionID + ".apk");
        }
        else
        {
            MessageBox.Show(Strings.loginFirst);
        }
    }
    private void DownloadApp()
    {
        app.installs++;
        if (userApp == null)
        {
            userApp = new UserApp();
            userApp.User = db.Users.Single(u => u.email.Equals(loginEmail));
            userApp.App = app;
            userApp.rate = 0;
            userApp.favorite = false;
            db.UserApps.InsertOnSubmit(userApp);
        }
        try
        {
            db.SubmitChanges();
        }
        catch (SqlException ex)
        {
            MessageBox.Show(Strings.unknownErrorInDatabase);
        }     
    }
    protected void RateApp(object sender, RatingEventArgs e)
    {
        if (loggedIn)
        {
            if (userApp != null)
            {
                userApp = app.UserApps.SingleOrDefault(ua => ua.User.email.Equals(loginEmail));
                userApp.rate = Int32.Parse(e.Value);
                yourRate_rating.CurrentRating = Convert.ToInt32(Math.Round((Double)userApp.rate));
                var rates = from ua in app.UserApps
                            where !ua.rate.Equals(0) && !ua.rate.Equals(null)
                            select ua.rate;
                app.rate = rates.Average();
                averageRate_rating.CurrentRating = Convert.ToInt32(Math.Round((Double)app.rate));
                app.ratesCount = rates.Count();
                ratesCount_Label.Text = app.ratesCount.ToString();
                try
                {
                    db.SubmitChanges();
                }
                catch (SqlException ex)
                {
                    MessageBox.Show(Strings.unknownErrorInDatabase);
                }
            }
            else
            {
                MessageBox.Show(Strings.installApp);
            }
        }
        else
        {
            MessageBox.Show(Strings.loginFirst);
        }
    }
}