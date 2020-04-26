DEPENDS_prepend = "yarn-native "
RDEPENDS_${PN}_prepend = "nodejs "

YARN_OFFLINE_DIR = "${DL_DIR}/yarn"
YARN_SHARED_DIR = "${TMPDIR}/work-shared/yarn"
YARN_RC = "${YARN_SHARED_DIR}/yarnrc"
YARN_CACHE = "${YARN_SHARED_DIR}/cache"
YARN_ARGS = "--use-yarnrc ${YARN_RC} --pure-lockfile --production=true"

# the package of node overlaps with the OE one, so adjust the OE one
PKGD = "${WORKDIR}/oe-package"

python() {
    bb.utils.mkdirhier(d.getVar("YARN_SHARED_DIR"))

    with open(d.getVar("YARN_RC"), "w") as f:
        f.write('yarn-offline-mirror "' + d.getVar("YARN_OFFLINE_DIR") + '"\n')
        f.write('cache-folder "' + d.getVar("YARN_CACHE") + '"\n')
        f.write('ignore-scripts true\n')
        f.write('disable-self-update-check true\n')
        f.write('child-concurrency "' + d.getVar("PARALLEL_MAKE") + '"\n')
}

# mmm, this will never work since do_unpack populates the source dir which runs later.
#do_fetch[postfuncs] += "yarn_fetch_dependencies"
#do_fetch[depends] += "yarn-native:do_populate_sysroot"

# move fetching dependencies to do configure for now...
do_configure() {
    yarn ${YARN_ARGS} --download-only --force install
}

do_compile() {
    yarn ${YARN_ARGS} --offline install
}

# FIXME: this blindly installs everything
do_install() {
    mkdir -p ${D}/${libdir}/${BPN}
    cp -r ${B}/* ${D}/${libdir}/${BPN}
}

FILES_${PN} += "${libdir}/${BPN}"
