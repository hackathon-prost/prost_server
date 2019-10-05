#!/bin/bash
read_user_params() {
    read -p "Enter your Service Name in PascalCase [eg. TemplateService]: " USER_SERVICE_NAME
    read -p "Enter your Package [eg. com.etermax.platform.template]: " USER_PACKAGE
    echo ""
}

create_or_replace_service_directory() {
    local BASE_PATH=$(cd .. && pwd)
    if [ -d "$BASE_PATH/$ID_SERVICE" ]; then
        echo "Creating new project => $ID_SERVICE"
        echo ""
        read -p "Directory $BASE_PATH/$ID_SERVICE already exists. Do you want to override it? [y/N]" result

        if [ "$result" == "Y" ] || [ "$result" == "y" ] ; then
            rm -rf $BASE_PATH/$ID_SERVICE
        else
            echo "Exiting cli ..."
            exit
        fi
    fi

    mkdir ../$ID_SERVICE
    cp -R . ../$ID_SERVICE
}

remove_junk_files() {
    rm -rf .git
    rm -rf .gradle/
    rm -rf .idea/
    find . -name '.DS_Store' -type f -exec rm {} \;
    find . -name '*.iml' -type f -exec rm {} \;
    rm -rf build/
    rm -rf out/
    rm -rf newrelic/logs
    rm service-template-cli.sh
}

create_and_replace_template_files() {
    local PACKAGE="com.etermax.platform.template"
    local TEST="TemplateTest"
    local TEST_FILE=$TEST".kt"
    local NEW_TEST=$USER_SERVICE_NAME"Test"
    local TEST_PATH="src/test/kotlin/com/etermax/platform/template/"
    local NEW_TEST_FILE=$USER_SERVICE_NAME"Test.kt"
    local NEWRELIC_APP_NAME="Platform \/ Template"
    
    mv $TEST_PATH$TEST_FILE $TEST_PATH$NEW_TEST_FILE
    
    sed -i.bak s/$TEST/$NEW_TEST/g $TEST_PATH$NEW_TEST_FILE
    sed -i.bak s/'kotlin-template'/$ID_SERVICE/g .gitlab-ci.yml
    sed -i.bak s/'kotlin-template'/$ID_SERVICE/g src/main/kotlin/com/etermax/platform/template/http/handler/GreetHandler.kt
    sed -i.bak s/'kotlin-template'/$ID_SERVICE/g src/main/kotlin/com/etermax/platform/template/http/handler/GreetPrivateHandler.kt
    sed -i.bak s/"$NEWRELIC_APP_NAME"/$USER_SERVICE_NAME/g newrelic/newrelic.yml
    sed -i.bak s/$PACKAGE/$USER_PACKAGE/g build.gradle
    sed -i.bak s/"kotlin-template"/$ID_SERVICE/g settings.gradle

    find src/ -type f -exec sed -i.bak "s/$PACKAGE/$USER_PACKAGE/g" {} \;

    # K8s
    find .k8s/staging/ -type f -exec sed -i.bak "s/"kotlin-template"/$ID_SERVICE/g" {} \;
    find .k8s/production/ -type f -exec sed -i.bak "s/"kotlin-template"/$ID_SERVICE/g" {} \;
    find .k8s/base/manifests/ -type f -exec sed -i.bak "s/"kotlin-template"/$ID_SERVICE/g" {} \;

    find . -name '*.bak' -type f -exec rm {} \;
}

replace_package_directories() {
    local MAIN_PATH_DIR="src/main/kotlin/"${USER_PACKAGE//.//}
    local TEST_PATH_DIR="src/test/kotlin/"${USER_PACKAGE//.//}

    mkdir -p src/main/kotlin/ERASEME
    mv src/main/kotlin/com src/main/kotlin/ERASEME
    mkdir -p $MAIN_PATH_DIR
    mv src/main/kotlin/ERASEME/com/etermax/platform/template/* $MAIN_PATH_DIR
    rm -rf src/main/kotlin/ERASEME

    mkdir -p src/test/kotlin/ERASEME
    mv src/test/kotlin/com src/test/kotlin/ERASEME
    mkdir -p $TEST_PATH_DIR
    mv src/test/kotlin/ERASEME/com/etermax/platform/template/* $TEST_PATH_DIR
    rm -rf src/test/kotlin/ERASEME
}

echo ""
echo "  _____                 _            _______                   _       _"
echo " / ____|               (_)          |__   __|                 | |     | |"
echo "| (___   ___ _ ____   ___  ___ ___     | | ___ _ __ ___  _ __ | | __ _| |_ ___"
echo " \___ \ / _ \ '__\ \ / / |/ __/ _ \    | |/ _ \ '_ \` _ \| '_ \| |/ _\` | __/ _ \\"
echo " ____) |  __/ |   \ V /| | (_|  __/    | |  __/ | | | | | |_) | | (_| | ||  __/"
echo "|_____/ \___|_|    \_/ |_|\___\___|    |_|\___|_| |_| |_| .__/|_|\__,_|\__\___|"
echo "                                                        | |                    "
echo "                                                        |_|                    "
echo ""

read_user_params

ID_SERVICE=$(echo $USER_SERVICE_NAME | sed 's/\(.\)\([A-Z]\)/\1-\2/g' | tr '[:upper:]' '[:lower:]')

# Remove .DS_Store files if exists to omit issues using sed.
find . -name '.DS_Store' -type f -exec rm {} \;

create_or_replace_service_directory

# Move to destination service directory.
cd ../$ID_SERVICE

remove_junk_files

create_and_replace_template_files

replace_package_directories

echo ""
echo "Your new project was created in "$(pwd)
