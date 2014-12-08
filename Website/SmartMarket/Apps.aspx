<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="Apps.aspx.cs" Inherits="Apps" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Apps</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:GridView ID="apps_GridView" runat="server" AllowPaging="True" AllowSorting="True"
            AutoGenerateColumns="False" DataKeyNames="appID" DataSourceID="apps_LinqDataSource"
            OnSelectedIndexChanged="DeleteApp" PageSize="15">
            <Columns>
                <asp:ImageField DataImageUrlField="appID" DataImageUrlFormatString="Files/Icons/{0}.png"
                    HeaderText="Icon">
                    <ControlStyle CssClass="iconImageField" />
                </asp:ImageField>
                <asp:BoundField DataField="name" HeaderText="Name" SortExpression="name" />
                <asp:BoundField DataField="packageName" HeaderText="Package Name" SortExpression="packageName" />
                <asp:BoundField DataField="installs" HeaderText="Installs" SortExpression="installs" />
                <asp:BoundField DataField="rate" HeaderText="Rate" SortExpression="rate" />
                <asp:BoundField DataField="ratesCount" HeaderText="Rates Count" SortExpression="ratesCount" />
                <asp:CheckBoxField DataField="published" HeaderText="Published" SortExpression="published" />
                <asp:HyperLinkField DataNavigateUrlFields="appID" DataNavigateUrlFormatString="EditApp.aspx?id={0}"
                    Text="Edit" />
                <asp:ButtonField CommandName="Select" Text="Delete" />
            </Columns>
            <HeaderStyle BackColor="#FFFF00" Font-Bold="True" ForeColor="#6C8085" />
        </asp:GridView>
        <asp:LinqDataSource ID="apps_LinqDataSource" runat="server" ContextTypeName="SmartMarketDataClassesDataContext"
            EntityTypeName="" OrderBy="name" TableName="Apps">
        </asp:LinqDataSource>
    </div>
</asp:Content>
