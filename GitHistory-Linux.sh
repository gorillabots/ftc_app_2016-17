#!/bin/bash
# This script can be run from "git bash" which runs when "Terminal" is clicked in SourceTree

# Visit "https://git-scm.com/docs/pretty-formats" for more information on how this works
#
# %C(color) prints everything in the specified color until it reaches %Creset
# %ad prints the commit date, and --date=short specifies the date format
# %s prints the commit message
# %an prints the name of the committer
git log --pretty=format:" [%C(bold green)%ad%Creset] %s <%C(bold yellow)%an%Creset>" --date=short

# Alternative format, %h prints the commit ID
# git log --pretty=format:"%h - [%C(bold yellow)%an%Creset - %C(bold red)%ad%Creset] %s" --date=short
