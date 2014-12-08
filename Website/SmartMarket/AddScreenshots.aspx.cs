using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Data.SqlClient;
using System.Drawing;

public partial class AddScreenshots : System.Web.UI.Page
{
    private App app;
    private SmartMarketDataClassesDataContext db;
    private int appID;
    private String screenshotsPath;
    private String tempScreenshotsPath;
    protected void Page_Load(object sender, EventArgs e)
    {
        screenshotsPath = Server.MapPath(Config.screenshotsPathNotMapped);
        tempScreenshotsPath = Server.MapPath(Config.tempScreenshotsPathNotMappped);     
        String loginEmail = HttpContext.Current.User.Identity.Name;
        if (Request.QueryString["id"] == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        appID = Int32.Parse(Request.QueryString["id"]);
        db = new SmartMarketDataClassesDataContext();
        app = db.Apps.SingleOrDefault(a => a.appID.Equals(appID));
        if (app == null)
        {
            String info = Strings.urlNotFound;
            Response.Redirect("Info.aspx?info=" + info);
        }
        User user = db.Users.SingleOrDefault(u => u.email.Equals(loginEmail));
        if (user == null || (!user.admin && !app.Developer.User.email.Equals(loginEmail)))
        {
            String info = Strings.accessDenied;
            Response.Redirect("Info.aspx?info=" + info);
        }
    }
    protected void upload_Button_Click(object sender, EventArgs e)
    {
        Helper.UploadFile(this, screenshot_FileUpload, tempScreenshotsPath, app.appID.ToString());
        String extension = Path.GetExtension(screenshot_FileUpload.PostedFile.FileName);
        if (app.Screenshots.Count < 15)
        {
            if (ScreenshotAcceptable(tempScreenshotsPath + app.appID.ToString() + extension))
            {
                Screenshot screenshot = new Screenshot();
                screenshot.App = app;
                screenshot.extension = extension;
                db.Screenshots.InsertOnSubmit(screenshot);
                try
                {
                    db.SubmitChanges();
                    File.Copy(tempScreenshotsPath + app.appID.ToString() + extension, screenshotsPath + screenshot.screenshotID.ToString() + extension, true);
                    screenshots_GridView.DataBind();
                }
                catch (SqlException ex)
                {
                    MessageBox.Show(Strings.unknownErrorInDatabase);
                }
            }
            else
            {
                MessageBox.Show(Strings.screenShotNotAccepted);
            }
            File.Delete(tempScreenshotsPath + app.appID.ToString() + extension);
        }
        else
        {
            MessageBox.Show(Strings.appCannotHaveMoreScreenshots);
        }
    }
    protected void OnScreenshotDelete(object sender, LinqDataSourceDeleteEventArgs e)
    {
        Screenshot s = (Screenshot)(e.OriginalObject);
        String filePath = screenshotsPath + s.screenshotID + s.extension;
        File.Delete(filePath);
    }
    protected void Continue_Button_Click(object sender, EventArgs e)
    {
        Response.Redirect("AddVersions.aspx?id=" + appID);
    }
    private Boolean ScreenshotAcceptable(String fileName)
    {
        Boolean result = false;
        Bitmap bitmap = new Bitmap(fileName);
        if
        (
            (bitmap.Width == 320 && bitmap.Height == 480)
            || (bitmap.Width == 480 && bitmap.Height == 800)
            || (bitmap.Width == 480 && bitmap.Height == 854)
            || (bitmap.Width == 1280 && bitmap.Height == 720)
            || (bitmap.Width == 1280 && bitmap.Height == 800)
        )
        {
            result = true;
        }
        bitmap.Dispose();
        return result;
    }
}