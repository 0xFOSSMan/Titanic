package net.minecraft.src;

import cc.noxiuam.titanic.client.network.profile.Profile;

import java.util.List;

public abstract class EntityPlayer extends EntityLiving {

    public InventoryPlayer inventory;
    public CraftingInventoryCB field_20069_g;
    public CraftingInventoryCB field_20068_h;
    public byte field_9371_f;
    public int score;
    public float field_775_e;
    public float field_774_f;
    public boolean isSwinging;
    public int swingProgressInt;
    public String field_771_i;
    public int dimension;
    public String field_20067_q;
    public double field_20066_r;
    public double field_20065_s;
    public double field_20064_t;
    public double field_20063_u;
    public double field_20062_v;
    public double field_20061_w;
    public EntityFish fishEntity;
    private int damageRemainder;

    public EntityPlayer(World world) {
        super(world);
        inventory = new InventoryPlayer(this);
        field_9371_f = 0;
        score = 0;
        isSwinging = false;
        swingProgressInt = 0;
        damageRemainder = 0;
        fishEntity = null;
        field_20069_g = new CraftingInventoryPlayerCB(inventory, !world.multiplayerWorld);
        field_20068_h = field_20069_g;
        yOffset = 1.62F;
        setLocationAndAngles((double) world.spawnX + 0.5D, world.spawnY + 1, (double) world.spawnZ + 0.5D, 0.0F, 0.0F);
        health = 20;
        field_9351_C = "humanoid";
        field_9353_B = 180F;
        fireResistance = 20;
        texture = "/mob/char.png";
    }

    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.multiplayerWorld && field_20068_h != null && !field_20068_h.func_20120_b(this)) {
            func_20059_m();
            field_20068_h = field_20069_g;
        }
        field_20066_r = field_20063_u;
        field_20065_s = field_20062_v;
        field_20064_t = field_20061_w;
        double d = posX - field_20063_u;
        double d1 = posY - field_20062_v;
        double d2 = posZ - field_20061_w;
        double d3 = 10D;
        if (d > d3) {
            field_20066_r = field_20063_u = posX;
        }
        if (d2 > d3) {
            field_20064_t = field_20061_w = posZ;
        }
        if (d1 > d3) {
            field_20065_s = field_20062_v = posY;
        }
        if (d < -d3) {
            field_20066_r = field_20063_u = posX;
        }
        if (d2 < -d3) {
            field_20064_t = field_20061_w = posZ;
        }
        if (d1 < -d3) {
            field_20065_s = field_20062_v = posY;
        }
        field_20063_u += d * 0.25D;
        field_20061_w += d2 * 0.25D;
        field_20062_v += d1 * 0.25D;
    }

    protected void func_20059_m() {
        field_20068_h = field_20069_g;
    }

    public void func_20046_s() {
        //field_20067_q = (new StringBuilder()).append("http://www.minecraft.net/cloak/get.jsp?user=").append(field_771_i).toString();
        skinUrl = field_20067_q;
    }

    public void func_350_p() {
        super.func_350_p();
        field_775_e = field_774_f;
        field_774_f = 0.0F;
    }

    public void preparePlayerToSpawn() {
        yOffset = 1.62F;
        setSize(0.6F, 1.8F);
        super.preparePlayerToSpawn();
        health = 20;
        deathTime = 0;
    }

    protected void updatePlayerActionState() {
        if (isSwinging) {
            swingProgressInt++;
            if (swingProgressInt == 8) {
                swingProgressInt = 0;
                isSwinging = false;
            }
        } else {
            swingProgressInt = 0;
        }
        swingProgress = (float) swingProgressInt / 8F;
    }

    public void onLivingUpdate() {
        if (worldObj.difficultySetting == 0 && health < 20 && (ticksExisted % 20) * 12 == 0) {
            heal(1);
        }
        inventory.decrementAnimations();
        field_775_e = field_774_f;
        super.onLivingUpdate();
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        float f1 = (float) Math.atan(-motionY * 0.20000000298023224D) * 15F;
        if (f > 0.1F) {
            f = 0.1F;
        }
        if (!onGround || health <= 0) {
            f = 0.0F;
        }
        if (onGround || health <= 0) {
            f1 = 0.0F;
        }
        field_774_f += (f - field_774_f) * 0.4F;
        field_9328_R += (f1 - field_9328_R) * 0.8F;
        if (health > 0) {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = (Entity) list.get(i);
                    if (!entity.isDead) {
                        func_451_h(entity);
                    }
                }

            }
        }
    }

    private void func_451_h(Entity entity) {
        entity.onCollideWithPlayer(this);
    }

    public int getScore() {
        return score;
    }

    public void onDeath(Entity entity) {
        super.onDeath(entity);
        setSize(0.2F, 0.2F);
        setPosition(posX, posY, posZ);
        motionY = 0.10000000149011612D;
        if (field_771_i.equals("Notch")) {
            dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }
        inventory.dropAllItems();
        if (entity != null) {
            motionX = -MathHelper.cos(((attackedAtYaw + rotationYaw) * 3.141593F) / 180F) * 0.1F;
            motionZ = -MathHelper.sin(((attackedAtYaw + rotationYaw) * 3.141593F) / 180F) * 0.1F;
        } else {
            motionX = motionZ = 0.0D;
        }
        yOffset = 0.1F;
    }

    public void addToPlayerScore(Entity entity, int i) {
        score += i;
    }

    public void func_20060_w() {
        dropPlayerItemWithRandomChoice(inventory.decrStackSize(inventory.currentItem, 1), false);
    }

    public void dropPlayerItem(ItemStack itemstack) {
        dropPlayerItemWithRandomChoice(itemstack, false);
    }

    public void dropPlayerItemWithRandomChoice(ItemStack itemstack, boolean flag) {
        if (itemstack == null) {
            return;
        }
        EntityItem entityitem = new EntityItem(worldObj, posX, (posY - 0.30000001192092896D) + (double) func_373_s(), posZ, itemstack);
        entityitem.delayBeforeCanPickup = 40;
        float f = 0.1F;
        if (flag) {
            float f2 = rand.nextFloat() * 0.5F;
            float f4 = rand.nextFloat() * 3.141593F * 2.0F;
            entityitem.motionX = -MathHelper.sin(f4) * f2;
            entityitem.motionZ = MathHelper.cos(f4) * f2;
            entityitem.motionY = 0.20000000298023224D;
        } else {
            float f1 = 0.3F;
            entityitem.motionX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
            entityitem.motionZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f1;
            entityitem.motionY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f1 + 0.1F;
            f1 = 0.02F;
            float f3 = rand.nextFloat() * 3.141593F * 2.0F;
            f1 *= rand.nextFloat();
            entityitem.motionX += Math.cos(f3) * (double) f1;
            entityitem.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            entityitem.motionZ += Math.sin(f3) * (double) f1;
        }
        joinEntityItemWithWorld(entityitem);
    }

    protected void joinEntityItemWithWorld(EntityItem entityitem) {
        worldObj.entityJoinedWorld(entityitem);
    }

    public float getCurrentPlayerStrVsBlock(Block block) {
        float f = inventory.getStrVsBlock(block);
        if (isInsideOfMaterial(Material.water)) {
            f /= 5F;
        }
        if (!onGround) {
            f /= 5F;
        }
        return f;
    }

    public boolean canHarvestBlock(Block block) {
        return inventory.canHarvestBlock(block);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Inventory");
        inventory.readFromNBT(nbttaglist);
        dimension = nbttagcompound.getInteger("Dimension");
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
        nbttagcompound.setInteger("Dimension", dimension);
    }

    public void displayGUIChest(IInventory iinventory) {
    }

    public void displayWorkbenchGUI(int i, int j, int k) {
    }

    public void onItemPickup(Entity entity, int i) {
    }

    public float func_373_s() {
        return 0.12F;
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        field_9344_ag = 0;
        if (health <= 0) {
            return false;
        }
        if ((entity instanceof EntityMobs) || (entity instanceof EntityArrow)) {
            if (worldObj.difficultySetting == 0) {
                i = 0;
            }
            if (worldObj.difficultySetting == 1) {
                i = i / 3 + 1;
            }
            if (worldObj.difficultySetting == 3) {
                i = (i * 3) / 2;
            }
        }
        if (i == 0) {
            return false;
        } else {
            return super.attackEntityFrom(entity, i);
        }
    }

    protected void damageEntity(int i) {
        int j = 25 - inventory.getTotalArmorValue();
        int k = i * j + damageRemainder;
        inventory.damageArmor(i);
        i = k / 25;
        damageRemainder = k % 25;
        super.damageEntity(i);
    }

    public void displayGUIFurnace(TileEntityFurnace tileentityfurnace) {
    }

    public void displayGUIEditSign(TileEntitySign tileentitysign) {
    }

    public void useCurrentItemOnEntity(Entity entity) {
        entity.interact(this);
    }

    public ItemStack getCurrentEquippedItem() {
        return inventory.getCurrentItem();
    }

    public void destroyCurrentEquippedItem() {
        inventory.setInventorySlotContents(inventory.currentItem, null);
    }

    public double getYOffset() {
        return (double) (yOffset - 0.5F);
    }

    public void swingItem() {
        swingProgressInt = -1;
        isSwinging = true;
    }

    public void attackTargetEntityWithCurrentItem(Entity entity) {
        int i = inventory.getDamageVsEntity(entity);
        if (i > 0) {
            entity.attackEntityFrom(this, i);
            ItemStack itemstack = getCurrentEquippedItem();
            if (itemstack != null && (entity instanceof EntityLiving)) {
                itemstack.hitEntity((EntityLiving) entity);
                if (itemstack.stackSize <= 0) {
                    itemstack.func_1097_a(this);
                    destroyCurrentEquippedItem();
                }
            }
        }
    }

    public void respawnPlayer() {
    }

    public void func_20058_b(ItemStack itemstack) {
    }

    public void setEntityDead() {
        super.setEntityDead();
        field_20069_g.onCraftGuiClosed(this);
        if (field_20068_h != null) {
            field_20068_h.onCraftGuiClosed(this);
        }
    }
}
