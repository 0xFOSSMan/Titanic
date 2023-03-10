package net.minecraft.src;

public class ItemSign extends Item {

    public ItemSign(int i) {
        super(i);
        maxDamage = 64;
        maxStackSize = 1;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
        if (l == 0) {
            return false;
        }
        if (!world.getBlockMaterial(i, j, k).func_878_a()) {
            return false;
        }
        if (l == 1) {
            j++;
        }
        if (l == 2) {
            k--;
        }
        if (l == 3) {
            k++;
        }
        if (l == 4) {
            i--;
        }
        if (l == 5) {
            i++;
        }
        if (!Block.signPost.canPlaceBlockAt(world, i, j, k)) {
            return false;
        }
        if (l == 1) {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.signPost.blockID, MathHelper.floor_double((double) (((entityplayer.rotationYaw + 180F) * 16F) / 360F) + 0.5D) & 0xf);
        } else {
            world.setBlockAndMetadataWithNotify(i, j, k, Block.signWall.blockID, l);
        }
        itemstack.stackSize--;
        TileEntitySign tileentitysign = (TileEntitySign) world.getBlockTileEntity(i, j, k);
        if (tileentitysign != null) {
            entityplayer.displayGUIEditSign(tileentitysign);
        }
        return true;
    }
}
