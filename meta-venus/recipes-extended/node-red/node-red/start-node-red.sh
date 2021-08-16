#!/bin/bash
exec 2>&1
echo "*** Starting node-red script ***"

NODE_RED="/data/home/root/.node-red"
DATA_MODULES="$NODE_RED/node_modules"
VICTRON="$DATA_MODULES/@victronenergy"
DEFAULTCONF="/usr/lib/node_modules/node-red/defaults"

if [ ! -d $NODE_RED ]; then
	mkdir $NODE_RED
	mkdir $DATA_MODULES
	cp "$DEFAULTCONF/settings.js" "$NODE_RED"
	sed -i "s/a-secret-key/`openssl rand -hex 32`/g" "$NODE_RED/settings.js"
fi

if [ -d $VICTRON ]; then
	rm -rf $VICTRON
fi

if [ ! -f $VICTRON ]; then
	touch $VICTRON
fi

exec /usr/lib/node_modules/node-red/red.js $@ --userDir ${NODE_RED}
