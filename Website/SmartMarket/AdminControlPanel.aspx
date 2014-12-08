<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="AdminControlPanel.aspx.cs" Inherits="AdminControlPanel" %>


<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Admin control panel</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:HyperLink ID="upps_HyperLink" runat="server" NavigateUrl="~/Apps.aspx">Apps</asp:HyperLink>
        <br />
        <br />
        <asp:HyperLink ID="users_HyperLink" runat="server" NavigateUrl="~/Users.aspx">Users</asp:HyperLink>
        <br />
        <br />
        <asp:HyperLink ID="trainData_HyperLink" runat="server" NavigateUrl="~/TrainData.aspx">Train Data</asp:HyperLink>
    </div>
</asp:Content>


