Crowd Conext plugin
===================
This plugin for Atlassian Crowd provides support for consuming SAML assertions - from Shibboleth - and adds group mappings with OpenConext groups.

OpenConext was developed by SURFnet as part of the SURFworks programme. SURFnet runs an instance of the platform for research and education in The Netherlands as SURFconext

OpenConext: [http://www.openconext.org](http://www.openconext.org)
SURFconext: [http://www.surfconext.nl](http://www.surfconext.nl)

## Building and running

To build this plugin, follow the regular Atlassian Plugin SDK steps:
- Download the SDK, set your PATH-variable to include the SDK bin path
- Clone this repository
- cd into the repository
- run atlas-package
- run atlas-run-standalone --product=crowd
- in another window, run atlas-install-plugin (from within the repository directory)

## Configuration

The plugin offers a - very basic - configuration interface, reachable from the regular Crowd management interface.
Click on the 'Conext' item in the navigation bar.

## Disclaimer

See the NOTICE file