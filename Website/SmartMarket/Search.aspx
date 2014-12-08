<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="Search.aspx.cs" Inherits="Search" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Smart Market</title>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
  
        <asp:GridView ID="apps_GridView" runat="server" AutoGenerateColumns="False" DataKeyNames="appID"
            DataSourceID="apps_LinqDataSource" AllowPaging="True" AllowSorting="True" 
            PageSize="15">
            <Columns>
                <asp:ImageField DataImageUrlField="appID" DataImageUrlFormatString="Files/Icons/{0}.png"
                    HeaderText="Icon">
                    <ControlStyle CssClass="iconImageField" />
                </asp:ImageField>
                <asp:BoundField DataField="name" HeaderText="Name" SortExpression="name" />
                <asp:BoundField DataField="installs" HeaderText="Installs" SortExpression="installs" />
                <asp:BoundField DataField="rate" HeaderText="Rate" SortExpression="rate" />
                <asp:BoundField DataField="ratesCount" HeaderText="Rates Count" SortExpression="ratesCount" />
                <asp:HyperLinkField DataNavigateUrlFields="packageName" DataNavigateUrlFormatString="AppDetails.aspx?package={0}"
                    Text="View" />
            </Columns>
        <HeaderStyle BackColor="#FFFF00" Font-Bold="True" ForeColor="#6C8085" />
        </asp:GridView>
        <asp:LinqDataSource ID="apps_LinqDataSource" runat="server" ContextTypeName="SmartMarketDataClassesDataContext"
            EntityTypeName="" OrderBy="installs desc" TableName="Apps">
        </asp:LinqDataSource>
        <br />
    </div>

  
</asp:Content>

