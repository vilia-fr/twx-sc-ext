# ThingWorx Windows Services extension

This is a simple ThingWorx extension wrapping `sc` command-line utility from Windows SDK (see details 
[in Windows Dev Center](https://docs.microsoft.com/en-us/windows/win32/services/controlling-a-service-using-sc)). 
Currently it supports only querying Windows services, allowing you to 
get the list and state of all configured services on the Windows box where ThingWorx is running. Usage is trivial:

```javascript
try {
    var windowsServices = Resources["WindowsServicesFunctions"].QueryServices();
} catch (ex) {
    // Unsupported platform (Linux, Docker, no SDK, etc.)
}
```

This returns an INFOTABLE of `WindowsService` shape like this:

| name            | description                  | type              | state   |
| --------------- | ---------------------------- | ----------------- | ------- |
| AdobeARMservice | Adobe Acrobat Update Service | WIN32_OWN_PROCESS | RUNNING |
| Appinfo         | Application Information      | WIN32             | RUNNING |
| AppMgmt         | Application Management       | WIN32             | RUNNING |


## Installing

1. Download [extension's ZIP file](https://raw.githubusercontent.com/vilia-fr/twxscext/master/build/distributions/windows-services-ext.zip)
2. Make sure that your ThingWorx is configured to allow installing server-side extensions (ThingWorx 8.4+)
3. Open ThingWorx Composer > Import/Export > Extensions > Import
4. Select the ZIP file, upload and refresh the browser page as suggested
5. You can use the extension, no need to restart the server

## Upgrading

To upgrade the extension, you'll need to repeat the same steps as for installing. Don't forget to restart the server afterwards.

## Removing

To remove the extension from your ThingWorx installation you'll need to:

1. Check your code to make sure that you don't use it anywhere (a handy way to do it would be exporting all your entities "to source control",
i.e. as a bunch of XML files and do a `grep` over those files, looking for `WindowsServicesFunctions`.
2. Uninstall it via Composer > Import/Export > Extensions > Manage
3. Restart the server

## Compatibility

Tested against the following versions of ThingWorx:

1. ThingWorx 7.4.0-b159
2. ThingWorx 8.4.3-b2219

Both on Windows 10 Version 1803 (OS Build 17134.885).

## Docker support

On Windows Docker containers run inside Hyper-V or VirtualBox VMs with MobyLinux, which means that technically speaking ThingWorx is running on Linux and
is not aware of the Windows hypervisor. Which in turn means it doesn't know anything about Windows' `sc` command, so this extension won't work.

## Dependencies

This extension depends only on the `sc` utility, available as part of [Windows SDK](https://developer.microsoft.com/en-us/windows/downloads/windows-10-sdk).
Make sure the SDK is installed before you use this extension. If you try using it without SDK or on Linux, you will get a runtime exception, so make sure you
catch it in the calling code.

There are no external Java dependencies.

## Building

1. Copy JAR files from ThingWorx Extension SDK into /twx-lib directory;
2. Execute `ant -f build-extension.xml`

## Disclaimer

Vilia does not support this extension, nor liable for any side effects of installing it on your ThingWorx system. 
Use it on your own risk. **DO NOT USE IT IN PRODUCTION ENVIRONMNENT!**
