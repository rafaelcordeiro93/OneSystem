cd C:\Program Files\wildfly-11.0.0.CR1\standalone\deployments
type nul >> OneSystem-war-1.0-SNAPSHOT.war.skipdeploy
xcopy /i /y /e /s C:\Users\Rafael\Documents\NetBeansProjects\OneSystem\target\OneSystem-war-1.0-SNAPSHOT.war
del *skipdeploy
type nul >> OneSystem-war-1.0-SNAPSHOT.war.dodeploy