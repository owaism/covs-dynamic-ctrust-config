#!/bin/sh

DYNO_HOME=/usr/lib/dyno
WEB_AGENT_LOCATION=/a01/dyno
SERVICE_URL=http://director.cf.covisintrnd.com/instances
REALM=CEXP
CT_SERVER=ct1.covisintrnd.com
TYPE=PROVISIONER
AGENT_ENABLED=true
OUTPUT_FILE=${WEB_AGENT_LOCATION}/resultsfile
CACHE_FILE=${WEB_AGENT_LOCATION}/webagent_serialize_file
LOG_FILE=${DYNO_HOME}/logback.xml
PATH_TO_DYNO_JAR=${DYNO_HOME}/dyno-apache-1.0.jar
ECHO=/bin/echo
JAVA_HOME=/a01/software/jdk1.7

run() {
  cmd_output=$($@)
  local status=$?
  if [ $status -ne 0 ]
  then
    echo "$* failed with exit code $?"
    rm ${CACHE_FILE}
  else
    if [[ -s $OUTPUT_FILE ]] ; then
       CONTENTS=$(cat "$OUTPUT_FILE")
       mv $CONTENTS $WEB_AGENT_LOCATION/webagent.conf
       cp $WEB_AGENT_LOCATION/webagent.conf /a01/conf/ctrust/
       /a01/software/apache/bin/apachectl graceful
    else
       echo "$OUTPUT_FILE is empty. Exiting from script..."
    fi ;
  fi
}

run ${JAVA_HOME}/bin/java -DWEB_AGENT_CONF_LOCATION=${WEB_AGENT_LOCATION} -DCONFIGURATION_SERVICE_URL=${SERVICE_URL} -DDEFAULT_REALM=${REALM} -DCTRUST_SERVER=${CT_SERVER} -DSERVER_TYPE=${TYPE} -DENABLE_CTRUST_AGENT=${AGENT_ENABLED} -DRESULTS_FILE=${OUTPUT_FILE} -DCACHE_FILE_LOCATION=${CACHE_FILE} -Dlogback.configurationFile=${LOG_FILE} -jar ${PATH_TO_DYNO_JAR} >/dev/null
