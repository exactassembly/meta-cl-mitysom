# Simple initramfs image suitable for booting the MitySOM5CSE
#
# 
SUMMARY = "Initramfs image supporting overlayfs"
DESCRIPTION = "Small image capable of booting a MitySOM5 module entirely from \
in-memory pseudo-root. U-Boot will load the kernel, FDT and the initramfs, \
then an initscrip sets up the overlayfs and root-pivots to a non-writable \
squashfs with small non-volatile storage using overlayfs."

LICENSE = "BSD-3"
DEPENDS = "virtual/kernel"
RDEPENDS:${PN} = "busybox"

COMPATIBLE_MACHINE = "mitysom5cse"

INITRAMFS_SCRIPTS += "\
    initramfs-framework-base \
    initramfs-module-udev \
    initramfs-module-overlayfs \
    "

VIRTUAL-RUNTIME_dev_manager ?= "busybox-mdev"
VIRTUAL-RUNTIME_init_manager = "busybox"

PACKAGE_INSTALL += "\
    ${INITRAMFS_SCRIPTS} \
    packagegroup-core-boot \
    dropbear \
    ${VIRTUAL-RUNTIME_base-utils} \
    ${VIRTUAL-RUNTIME_dev_manager} \
    kernel-modules \
    base-passwd \
    ${ROOTFS_BOOTSTRAP_INSTALL}"

export IMAGE_BASENAME = "initramfs"
IMAGE_NAME_SUFFIX ?= ""
IMAGE_LINGUAS = ""
# Do not pollute the initrd image with rootfs features
IMAGE_FEATURES = ""
#PACKAGE_INSTALL = ""
IMAGE_LINGUAS = ""
IMAGE_FSTYPES = "cpio.gz.u-boot"
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

# Use the same restriction as initramfs-live-install
COMPATIBLE_HOST = "(i.86|x86_64|aarch64|arm).*-linux"
inherit core-image

# Use the same restriction as initramfs-live-install
COMPATIBLE_HOST = "(i.86|x86_64|aarch64|arm).*-linux"

ROOTFS_POSTPROCESS_COMMAND += " rootfs_tuning_filesystem;"

rootfs_tuning_filesystem() {
    # we dont want another copy of the bzImage boot file in here
    # plus the /boot directory will be hidden when we mount the real
    # EFI boot partition
    rm -f ${IMAGE_ROOTFS}/boot/*

    echo "MitySOM5CSE INITRAMFS" >> ${IMAGE_ROOTFS}/etc/issue
}