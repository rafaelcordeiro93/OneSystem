cd C:\Program Files\wildfly-11.0.0.CR1\standalone\deployments
type nul >> OneSystem-war-1.0-SNAPSHOT.war.skipdeploy
mkdir OneSystem-war-1.0-SNAPSHOT.war
cd OneSystem-war-1.0-SNAPSHOT.war
"C:\Program Files\Java\jdk1.8.0_144\bin\jar.exe" -xvf "C:\Users\Rafael\Documents\NetBeansProjects\OneSystem\target\OneSystem-war-1.0-SNAPSHOT.war"
cd ..
del *skipdeploy
type nul >> OneSystem-war-1.0-SNAPSHOT.war.dodeploy