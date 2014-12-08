<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true"
    CodeFile="Users.aspx.cs" Inherits="Users" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head_ContentPlaceHolder" runat="Server">
    <title>Users</title>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="body_ContentPlaceHolder" runat="Server">
    <div id="gallery">
        <br />
        <br />
        <br />
        <br />
        <br />
        <asp:GridView ID="users_GridView" runat="server" AllowPaging="True" AllowSorting="True"
            AutoGenerateColumns="False" DataKeyNames="userID" DataSourceID="Users_LinqDataSource"
            OnSelectedIndexChanged="DeleteUser" PageSize="15">
            <Columns>
                <asp:BoundField DataField="email" HeaderText="Email" SortExpression="email" />
                <asp:BoundField DataField="firstName" HeaderText="First Name" SortExpression="firstName" />
                <asp:BoundField DataField="lastName" HeaderText="Last Name" SortExpression="lastName" />
                <asp:BoundField DataField="gender" HeaderText="Gender" SortExpression="gender" />
                <asp:TemplateField HeaderText="Country" SortExpression="Country.name">
                    <ItemTemplate>
                        <asp:Label ID="Label1" runat="server" Text='<%# Bind("Country.name") %>'></asp:Label>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField ConvertEmptyStringToNull="False" HeaderText="Developer">
                    <ItemTemplate>
                        <asp:Label ID="Label1" runat="server" Text='<%# Bind("Developer") %>'></asp:Label>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:CheckBoxField DataField="admin" HeaderText="Admin" SortExpression="admin" />
                <asp:HyperLinkField DataNavigateUrlFields="email" DataNavigateUrlFormatString="EditUser.aspx?email={0}"
                    Text="Edit" />
                <asp:ButtonField CommandName="Select" Text="Delete" />
            </Columns>
            <HeaderStyle BackColor="#FFFF00" Font-Bold="True" ForeColor="#6C8085" />
        </asp:GridView>
        <asp:LinqDataSource ID="Users_LinqDataSource" runat="server" ContextTypeName="SmartMarketDataClassesDataContext"
            EnableDelete="True" EntityTypeName="" TableName="Users" OrderBy="email">
        </asp:LinqDataSource>
    </div>
</asp:Content>
