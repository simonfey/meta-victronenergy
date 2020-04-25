DESCRIPTION = "Node-RED"
HOMEPAGE = "http://nodered.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6f37569f5013072e9490d2194d10ae6"

SRC_URI = "https://registry.yarnpkg.com/${BPN}/-/${BPN}-${PV}.tgz"
SRC_URI += "file://0001-yarn.lock.patch"
SRC_URI[md5sum] = "a857f445c395a62152a152d3631d6351"
SRC_URI[sha256sum] = "19be616f531976d121815ee56eebcb0ed2e51c72b0fb2a4384efe3ec65f1a537"

RDEPENDS_${PN} += "bash"

inherit yarn

S = "${WORKDIR}/package"

