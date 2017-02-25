package superdopesquad.superdopejedimod.entity;


import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import superdopesquad.superdopejedimod.SuperDopeJediMod;
import superdopesquad.superdopejedimod.faction.FactionInfo;
import superdopesquad.superdopejedimod.weapon.PlasmaShotEntity;


public class RepublicSentryDroidEntity extends RepublicBaseDroidEntity implements IRangedAttackMob {
		
	
	public RepublicSentryDroidEntity(World worldIn) {
		
		super(worldIn, "republicSentryDroidEntity", "Republic Sentry Droid");
		
		//this.setupAI();
		
		// This sets the bounding box size, not the actual model that you see rendered.
		this.setSize(1.0F, 2.0F);
		
		// how much experience do you get it you kill it?
		//this.experienceValue = 5;
		
		// Properties that we need to have later.
		//this.shadowSize = 1.0F;
		
		// Put a gaffi stick in his mainhand slot.
		//this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(SuperDopeJediMod.gaffiStick));
		
		// Customize this properties in daughter classes to get different behaviors.
		this.movementSpeed = 0.0; // This renders this droid unmoveable.
	}
	
	
	@Override
	public void registerEntityRender() {
			
		Class renderBaseClass = RepublicSentryDroidRender.class;
		Class modelBaseClass = RepublicSentryDroidModel.class;
		EntityRenderFactory factory = new EntityRenderFactory(renderBaseClass, modelBaseClass, this.shadowSize);
		RenderingRegistry.registerEntityRenderingHandler(this.getClass(), factory);
	}
	
	
	// set up AI tasks
	@Override
	protected void setupAI() {
			
		// clear any tasks assigned in super classes.
		//clearAITasks(); 
		super.setupAI();
			
	   // Main AI task list: shoot to kill any empire you see.
	   //this.tasks.addTask(1, new EntityAIAttackMeleeFactionAware(this, 1.0, false, 
		//	   SuperDopeJediMod.classManager.getFactionInfo(SuperDopeJediMod.classManager.FACTION_EMPIRE)));
	
		   //public EntityAIAttackRangedFactionAware(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, 
		   // 		float maxAttackDistanceIn, FactionInfo factionToAttack)
		 
		
	   this.tasks.addTask(1, new EntityAIAttackRangedFactionAware(this, this.movementSpeed, 1, 3, 10,
			   SuperDopeJediMod.classManager.getFactionInfo(SuperDopeJediMod.classManager.FACTION_EMPIRE)));
	
	   
	   // When not attacking the Empire, stare at the closest person.
	   this.tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 6.0F, 0.02F));

	   // Set up the targetTasks list, which defines who the entity focuses his actions on.
	   // Priority 0: attack anything that attacked me.
	   this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));   
	   // Priority 1: attack the nearest player I can find.
	   this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}
	
	
	 /**
     * Attack the specified entity using a ranged attack.
     *  
     * @param distanceFactor How far the target is, normalized and clamped between 0.1 and 1.0
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
    	float damageAmount = 1;
    	SuperDopeJediMod.weaponManager.ThrowPlasmaShot(worldObj, this, target, distanceFactor, damageAmount);
    }

	
	// After it dies, what equipment should it drop?
	@Override
	protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
		
		this.entityDropItem(new ItemStack(SuperDopeJediMod.entityManager.droidKit), 0);
		this.entityDropItem(new ItemStack(SuperDopeJediMod.entityManager.republicSentryDroidHead), 0);
    }
}