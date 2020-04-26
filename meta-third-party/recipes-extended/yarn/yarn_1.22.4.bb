#SRC_URI = "https://github.com/yarnpkg/yarn/releases/download/v${PV}/${BPN}-v${PV}.tar.gz"
SRC_URI = "file://yarn.tgz"
SRC_URI[md5sum] = "faf483d50aa8ccbdc802efa0cac5d4d3"
SRC_URI[sha256sum] = "bc5316aa110b2f564a71a3d6e235be55b98714660870c5b6b2d2d3f12587fb58"

inherit npm

#S = "${WORKDIR}/${BPN}-v${PV}"
S = "${WORKDIR}/dist"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=9afb19b302b259045f25e9bb91dd34d6"

BBCLASSEXTEND = "native"

