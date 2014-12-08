using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI;
using ICSharpCode.SharpZipLib.Zip;
using System.Net.Mail;
using System.Web.Mail;
using Gma.QrCodeNet.Encoding;
using Gma.QrCodeNet.Encoding.Windows.Controls;
using System.Drawing;
using System.Drawing.Imaging;


public static class Helper
{
    public static void UploadFile(Page context, FileUpload fileUpload, String serverPath, String savedFileName)
    {
        String uploadedFileName = fileUpload.PostedFile.FileName;
        String extension = Path.GetExtension(uploadedFileName);
        fileUpload.SaveAs(serverPath + savedFileName + extension);
    }
    public static void DownloadFile(String fileName, String downloadedFileName, String contentType)
    {
        if (downloadedFileName == null)
        {
            downloadedFileName = System.IO.Path.GetFileName(fileName);
        }
        byte[] fileBytes = System.IO.File.ReadAllBytes(fileName);
        System.Web.HttpContext context = System.Web.HttpContext.Current;
        context.Response.Clear();
        context.Response.ClearHeaders();
        context.Response.ClearContent();
        context.Response.AppendHeader("content-length", fileBytes.Length.ToString());
        context.Response.ContentType = contentType;
        context.Response.AppendHeader("content-disposition", "attachment; filename=" + downloadedFileName);
        context.Response.BinaryWrite(fileBytes);
        context.ApplicationInstance.CompleteRequest();
    }
    public static void SendMail(String from, String to, String subject, String messageText)
    {
        SmtpMail.Send(from, to, subject, messageText);
    }
    public static System.Drawing.Image GenerateQRCode(String data)
    {
        QrEncoder qrEncoder = new QrEncoder(ErrorCorrectionLevel.H);
        QrCode qrCode = new QrCode();
        qrEncoder.TryEncode(data, out qrCode);
        Renderer renderer = new Renderer(5, Brushes.Black, Brushes.White);
        MemoryStream ms = new MemoryStream();
        renderer.WriteToStream(qrCode.Matrix, ms, ImageFormat.Png);
        return System.Drawing.Image.FromStream(ms);
    }
    public static String ExecuteCommand(String fileName, String arguments)
    {
        StringBuilder Result = new StringBuilder();
        Process process = new Process();
        process.StartInfo.FileName = fileName;
        process.StartInfo.Arguments = arguments;
        process.StartInfo.UseShellExecute = false;
        process.StartInfo.CreateNoWindow = true;
        process.StartInfo.RedirectStandardInput = true;
        process.StartInfo.RedirectStandardOutput = true;
        process.Start();
        StreamReader streamReader = process.StandardOutput;
        while (!streamReader.EndOfStream)
        {
            Result.AppendLine(streamReader.ReadLine());
        }
        process.WaitForExit();
        process.Close();
        return Result.ToString();
    }
    public static String GetApkPropertyValue(string aaptOutput, String Property)
    {
        String result = "";
        int indexOfProperty = aaptOutput.IndexOf(Property);
        if (indexOfProperty >= 0)
        {
            int i = indexOfProperty + Property.Length + 1;
            Char c = aaptOutput[i];
            while (c != '\'')
            {
                result += c;
                i++;
                c = aaptOutput[i];
            }
        }
        else
        {
            result = null;
        }
        return result;
    }
    public static void GetApkIcon(String aaptPath, String fileName, String tempDirectoryNotMapped, String destinationDirectory, String savedIconName)
    {
        System.Web.HttpContext context = System.Web.HttpContext.Current;
        String tempDirectory = context.Server.MapPath(tempDirectoryNotMapped);
        String aaptOutput = Helper.ExecuteCommand(aaptPath, "dump badging " + fileName);
        String icon = Helper.GetApkPropertyValue(aaptOutput, "icon=");
        FastZip fastZip = new FastZip();
        fastZip.ExtractZip(fileName, tempDirectory , icon);
        String iconFullPath = context.Server.MapPath(tempDirectoryNotMapped + "/" + icon);
        File.Copy(iconFullPath, destinationDirectory + savedIconName + ".png", true);
        DirectoryInfo deleteDirectory = new DirectoryInfo(tempDirectory);
        deleteDirectory.Delete(true);
    }
}