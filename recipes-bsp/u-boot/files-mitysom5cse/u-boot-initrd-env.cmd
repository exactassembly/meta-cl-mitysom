initphy=gpio c 28; sleep 0.5; gpio s 28; sleep 0.5;
bootcmd=run initphy;gpio set 0;gpio clear 9; gpio set 9;run fpgaload;run mmcload; run mmcboot
bootimage=zImage
fdt_addr_r=0x01000000
fdtimage=socfpga_mitysom5cse_l23y8_empty.dtb
fpga2sdram=0xffc25080
fpga2sdram_apply=0x1ff7770c
fpga2sdram_handoff=0x00000000
fpgaintf=0xffd08028
fpgaintf_handoff=0x00000000
fpgaloadsize=0x6AEBE4
fpgaloadaddr=0x2000000
fpgaloadfile=5cse_l2_3y8.rbf
fpgaload=${mmcloadcmd} mmc 0:${mmcloadpart} ${fpgaloadaddr} ${fpgaloadfile}; bridge disable;fpga load 0 ${fpgaloadaddr} ${fpgaloadsize}; bridge enable
initramfsaddr=0x10000000
initrd_high=0x1F000000
initramfsfile=initramfs-mitysom5cse.cpio.gz.u-boot
l3remap=ff800000
l3remap_handoff=0x00000011
loadaddr=0x02000000
loopimgdev=/dev/mmcblk0p3
loopimgfile=application-rootfs-mitysom5cse.squashfs
looppersistdev=/dev/mmcblk0p4
mmcloadpart=3
mmcloadcmd=fatload
mmcload=mmc rescan;${mmcloadcmd} mmc 0:${mmcloadpart} ${loadaddr} ${bootimage};${mmcloadcmd} mmc 0:${mmcloadpart} ${fdt_addr_r} ${fdtimage};${mmcloadcmd} mmc 0:${mmcloadpart} ${initramfsaddr} ${initramfsfile}
mmcboot=setenv bootargs "console=ttyS0,115200 debug rdinit=/init root=/dev/ram loopimgdev=/dev/mmcblk0p3 loopimgfile=${loopimgfile} looppersistdev=${looppersistdev}"; bootz ${loadaddr} ${initramfsaddr} ${fdt_addr_r}