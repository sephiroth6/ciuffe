#!/bin/bash

	git add TrainServer/src/guiSrv/
	
	echo "aggiunti file del server"
	sleep 0.35

	git add TrainClient/src/train/
	git add TrainClient/src/guiCli/
	echo "aggiunti file del client"
	sleep 0.35
	
	git add Lib/
	git add CiuffeLib/src/lib/
	echo "aggiunti file libreria"
	sleep 0.35

	echo -n "Inserire il testo del commit: "
	read p
	git commit -m '$p'
	git push
	echo "total push effettuato"
	exit 0
	
	
