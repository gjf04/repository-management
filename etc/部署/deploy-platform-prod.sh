#! /bin/bash  # employ bash shell
cd /home/haieradmin/projects
echo "开始停止服务"
./mobile-web/bin/mobile-web stop
sleep 10
echo "开始删除旧服务"
rm -rf mobile-web*
sleep 2
echo "开始从jenkins上下载服务包"
wget http://jenkins.rrsjk.com/view/lexin-prod/job/lx-mobile-web-prod/lastSuccessfulBuild/artifact/target/mobile-web.tar.gz
echo "开始从解压"
tar -xvf mobile-web.tar.gz
cd /home/haieradmin/projects/mobile-web/bin
chmod 755 * ./
echo "启动服务"
/home/haieradmin/projects/mobile-web/bin/mobile-web start
sleep 5
tail -300f /var/log/rrslx/mobile-web.log
