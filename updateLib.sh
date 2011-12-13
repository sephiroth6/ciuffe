#!/bin/bash
	
	if -e CiuffeLib/dist/CiuffeLib.jar
		mv -r CiuffeLib/dist/CiuffeLib.jar Lib/
		echo "libreria aggiornata!"
	else echo "libreria già aggiornata, creare nuovo file jar!"
	
	exit 0
	
