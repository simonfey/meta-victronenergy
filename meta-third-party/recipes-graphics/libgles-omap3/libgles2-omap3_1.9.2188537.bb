DESCRIPTION = "Userspace libraries for PowerVR SGX chipset on TI SoCs"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://TSPA.txt;md5=c0d5d9c1e38b41677144c4e24d6ddee1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

require recipes-kernel/omap-sgx/ti-graphics-sdk-4_09_00_01_hardfp.inc

SRC_URI += " \
    file://egl.pc \
    file://glesv1_cm.pc \
    file://glesv2.pc \
    file://powervr.ini \
    file://rc.pvr \
"

PROVIDES += "virtual/egl virtual/libgles2"

DEPENDS = "patchelf-native"
RDEPENDS_${PN} = "kernel-module-ti-omap3-sgx-fbdev"

S = "${WORKDIR}/git"
SRC = "${S}/gfx_rel_es3.x"

INITSCRIPT_NAME = "rc.pvr"
INITSCRIPT_PARAMS = "start 95 S ."

inherit update-rc.d

do_install () {
    # SONAMEs are missing, but OE wants them for shlibs, so add them..
    install_so() {
        patchelf --set-soname $1 ${SRC}/$1
        install -m 755 ${SRC}/$1 ${D}/${libdir}
    }

    mkdir -p ${D}/${libdir}
    install_so libsrv_init.so
    install_so libsrv_um.so
    install_so libglslcompiler.so
    install_so libPVRScopeServices.so
    install_so libGLESv2.so
    install_so libEGL.so
    install_so libIMGegl.so
    install_so libusc.so
    install_so libpvrPVR2D_FRONTWSEGL.so
    install_so libpvr2d.so

    install -d ${D}${includedir}
    cp -r ${S}/include/OGLES2/* ${D}${includedir}

    sed -i -e "s:PREFIX:${prefix}:" -e "s:VERSION:${PV}:" ${WORKDIR}/*.pc
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/*pc ${D}${libdir}/pkgconfig

    install -d ${D}${bindir}
    install ${SRC}/pvrsrvctl ${D}${bindir}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/powervr.ini ${D}${sysconfdir}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/rc.pvr ${D}${sysconfdir}/init.d
}

FILES_${PN} = "${bindir} ${libdir}/lib*.so ${sysconfdir}"
FILES_${PN}-dev = "${includedir} ${libdir}/pkgconfig"

INSANE_SKIP_${PN} += "already-stripped"

