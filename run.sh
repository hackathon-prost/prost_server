#!/bin/sh

ENABLE_NEWRELIC=${ENABLE_NEWRELIC:-false}
NEWRELIC_ENVIRONMENT=${NEWRELIC_ENVIRONMENT:-production}

NEWRELIC_OPTS="-javaagent:newrelic/newrelic.jar -Dnewrelic.environment=$NEWRELIC_ENVIRONMENT"

if [ $ENABLE_NEWRELIC == "true" ]; then
    JAVA_OPTS="$JAVA_OPTS $NEWRELIC_OPTS"
fi

exec java $JAVA_OPTS -jar app.jar