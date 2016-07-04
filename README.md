Step1:在本地添加maven依赖

    <mirror>
        <id>central</id>
        <mirrorOf>*</mirrorOf>
        <url>https://wdnexus.mapbar.com/content/groups/public/</url>
    </mirror>

Step2:将项目cacerts证书，复制到本地JDK目录java_home\jre\lib\security

Step3:maven compile install

Step4:启动

      opentsp-gateway-web   Application.java, VM options:-Dspring.profiles.active=local

      opentsp-helloword-core   Application.java, VM options:-Dspring.profiles.active=local

Step5:访问http://localhost:8090/hello/getTime?param=1

徐冰浩