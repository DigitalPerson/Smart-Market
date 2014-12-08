using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MatrixNamespace;

/// <summary>
/// Summary description for Class1
/// </summary>
public class RecommendationSystem
{
    private int featuresNum;
    private double jValue;
    private double alpha;
    private double lambda;
    private double epsilon;
    private Matrix yMat;
    private Matrix rMat;
    private Matrix xMat;
    private Matrix tMat;
    private SmartMarketDataClassesDataContext db;
    private List<App> apps;
    private int appsNum;
    private List<int> appsIDs;
    private List<User> users;
    private int usersNum;
    private List<int> usersIDs;
    private List<UserApp> userApps;

    public RecommendationSystem()
    {
        featuresNum = 1 + 4;
        jValue = 0;
        alpha = 1e-4;
        lambda = 2;
        epsilon = 0.01;
        db = new SmartMarketDataClassesDataContext();
        FillData();
    }
    private void FillData()
    {
        apps = db.Apps.Where(a => a.published).ToList();
        appsNum = apps.Count();
        appsIDs = new List<int>();
        foreach (var app in apps)
        {
            appsIDs.Add(app.appID);
        }
        users = db.Users.ToList();
        usersNum = users.Count();
        usersIDs = new List<int>();
        foreach (var user in users)
        {
            usersIDs.Add(user.userID);
        }
        yMat = new Matrix(appsNum, usersNum);
        rMat = new Matrix(appsNum, usersNum);
        userApps = db.UserApps.ToList();
        UserApp userApp;
        for (int i = 0; i < appsNum; i++)
        {
            for (int j = 0; j < usersNum; j++)
            {
                userApp = userApps.SingleOrDefault(ua => ua.appID.Equals(appsIDs[i]) && ua.userID.Equals(usersIDs[j]));
                if (userApp != null)
                {
                    yMat[i, j] = userApp.rate;
                }
                else
                {
                    yMat[i, j] = 0;
                }
                if (yMat[i, j] == 0)
                {
                    rMat[i, j] = 0;
                }
                else
                {
                    rMat[i, j] = 1;
                }
            }
        }
        InitializeFeatures();
    }
    private void InitializeFeatures()
    {
        xMat = new Matrix(appsNum, featuresNum);
        tMat = new Matrix(usersNum, featuresNum);
        double[] max = new double[featuresNum];
        for (int i = 0; i < max.Length; i++)
        {
            max[i] = 0;
        }
        for (int i = 0; i < appsNum; i++)
        {
            for (int j = 0; j < featuresNum; j++)
            {
                switch (j)
                {
                    case 0:
                        xMat[i, j] = 1;
                        break;
                    case 1:
                        xMat[i, j] = apps[i].categoryID;
                        break;
                    case 2:
                        xMat[i, j] = apps[i].developerID;
                        break;
                    case 3:
                        xMat[i, j] = apps[i].installs;
                        break;
                    case 4:
                        xMat[i, j] = apps[i].rate * apps[i].ratesCount;
                        break;
                }
                if (j >= 1)
                {
                    if (xMat[i, j] > max[j])
                    {
                        max[j] = xMat[i, j];
                    }
                }
            }
        }
        for (int i = 0; i < appsNum; i++)
        {
            for (int j = 1; j < featuresNum; j++)
            {
                xMat[i, j] /= max[j];
            }
        }
        Random r = new Random();
        for (int i = 0; i < usersNum; i++)
        {
            for (int j = 0; j < featuresNum; j++)
            {
                tMat[i, j] = r.NextDouble();
            }
        }
    }
    private void Train()
    {
        CalculateT();
        CalculateJ();
    }
    private void CalculateT()
    {
        for (int j = 0; j < usersNum; j++)
        {
            Matrix temp2 = new Matrix(featuresNum, 1);
            for (int i = 0; i < appsNum; i++)
            {
                Matrix tRow = Matrix.Transpose(Matrix.getRow(tMat, j));
                Matrix xRow = Matrix.Transpose(Matrix.getRow(xMat, i));
                Matrix temp = Matrix.Transpose(tRow) * xRow;
                double temp1 = (temp[0, 0] - yMat[i, j]) * rMat[i, j];
                temp2 = temp2 + temp1 * xRow;
            }
            temp2 = temp2 + lambda * Matrix.Transpose(Matrix.getRow(tMat, j));
            temp2 = alpha * temp2;
            for (int k = 0; k < featuresNum; k++)
            {
                tMat[j, k] = tMat[j, k] - temp2[k, 0];
            }
        }
    }
    private void CalculateJ()
    {
        double temp1 = 0;
        double temp2 = 0;
        for (int j = 0; j < usersNum; j++)
        {
            for (int i = 0; i < appsNum; i++)
            {
                Matrix tRow = Matrix.Transpose(Matrix.getRow(tMat, j));
                Matrix xRow = Matrix.Transpose(Matrix.getRow(xMat, i));
                Matrix temp = Matrix.Transpose(tRow) * xRow;
                temp1 += (temp[0, 0] - yMat[i, j]) * rMat[i, j] * (temp[0, 0] - yMat[i, j]) * rMat[i, j];
            }
        }
        for (int j = 0; j < usersNum; j++)
        {
            for (int k = 0; k < featuresNum; k++)
            {
                temp2 += tMat[j, k] * tMat[j, k];
            }
        }
        jValue = 0.5 * temp1 + lambda / 2.0 * temp2;
    }
    public void TrainData()
    {
        jValue = 1000;
        double oldJValue = jValue;
        db.UserFeatures.DeleteAllOnSubmit(db.UserFeatures);
        db.Recommendations.DeleteAllOnSubmit(db.Recommendations);
        for (int i = 0; i < 10000; i++)
        {
            oldJValue = jValue;
            Train();
            if (Math.Abs(oldJValue - jValue) < epsilon)
            {
                break;
            }
            double newJValue = jValue;
            if (newJValue < oldJValue)
            {
                alpha *= 1.5;
            }
            else if (newJValue > oldJValue)
            {
                if (newJValue - oldJValue > (0.5 * oldJValue))
                    alpha *= 0.1;
                else
                    alpha *= 0.5;
            }
        }
        //storing users features 
        UserFeature userFeature;
        for (int i = 0; i < tMat.NoRows; i++)
        {
            for (int j = 0; j < tMat.NoCols; j++)
            {
                userFeature = new UserFeature();
                userFeature.featureID = j;
                userFeature.userID = usersIDs[i];
                userFeature.featureValue = (float)tMat[i, j];
                db.UserFeatures.InsertOnSubmit(userFeature);
            }
        }
        //storing recommendations
        Matrix tRow;
        Matrix xRow;
        for (int j = 0; j < yMat.NoCols; j++)
        {
            if (!NewUser(j))
            {
                for (int i = 0; i < yMat.NoRows; i++)
                {
                    if (rMat[i, j] == 0)
                    {
                        Recommendation recommendation = new Recommendation();
                        recommendation.appID = appsIDs[i];
                        recommendation.userID = usersIDs[j];
                        tRow = Matrix.getRow(tMat, j);
                        xRow = Matrix.getRow(xMat, i);
                        Matrix expectedRate = tRow * Matrix.Transpose(xRow);
                        recommendation.expectedRate = (float)expectedRate[0, 0];
                        db.Recommendations.InsertOnSubmit(recommendation);
                    }
                }
            }
        }
        db.SubmitChanges();
    }
    private bool NewUser(int j)
    {
        for (int i = 0; i < yMat.NoRows; i++)
        {
            if (rMat[i, j] > 0)
                return false;
        }
        return true;
    }
}