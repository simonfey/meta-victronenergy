DESCRIPTION = "Out of tree Kernel drivers for the PowerVR SGX chipset found in the TI am3517 / omap3"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://GFX_Linux_KM/GPL-COPYING;md5=60422928ba677faaa13d6ab5f5baaa1e"

inherit module

require ti-graphics-sdk-4_09_00_01_hardfp.inc

EXTRA_OEMAKE += "OMAPES=3.x BUILD=release FBDEV=yes SUPPORT_XORG=0 CROSS_COMPILE=${TARGET_PREFIX} HOME=$PWD LINUXKERNEL_INSTALL_DIR=${STAGING_KERNEL_DIR} GRAPHICS_INSTALL_DIR=${S} buildkernel"

do_install() {
    mkdir -p ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/sgx
    install -m 755 ${B}/GFX_Linux_KM/pvrsrvkm.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/sgx
    install -m 755 ${B}/GFX_Linux_KM/services4/3rdparty/dc_omapfb3_linux/omaplfb.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/sgx
}

