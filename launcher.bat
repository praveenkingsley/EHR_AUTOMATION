taskkill /f /im chrome.exe
taskkill /f /im chromedriver.exe

pushd %~dp0
call java -jar "Doctor_Module_Test-0.0.1-SNAPSHOT.jar"
popd

taskkill /f /im chrome.exe
taskkill /f /im chromedriver.exe
pause