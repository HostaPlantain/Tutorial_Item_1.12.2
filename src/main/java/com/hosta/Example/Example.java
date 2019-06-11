package com.hosta.Example;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Example.MOD_ID, name = "Example", version = "0.0.1")
public class Example {

	// ����MOD��ID
	public static final String MOD_ID = "example";

	// ����MOD��p�̃N���G�C�e�B�u�^�u
	public static final CreativeTabs TAB_EXAMPLE = new CreativeTabs(MOD_ID)
	{
		// �^�u�ɕ\�������Item���w�肷��
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ITEM_EXAMPLE);
		}
	};

	// �܂Ƃ߂Ċy�X�o�^�p�̃��X�g
	// �g��Ȃ����@�����邪�g���������y
	private static final List<Item> ITEMS = new ArrayList<Item>();

	// �ǉ�������Item��������
	// �K�v�ɉ�����Item���p�������N���X�����
	public static final Item ITEM_EXAMPLE		= new Item().setUnlocalizedName("item_example").setCreativeTab(TAB_EXAMPLE);
	public static final Item ITEM_EXAMPLE_TWO	= new Item().setUnlocalizedName("item_example_two").setCreativeTab(TAB_EXAMPLE);

	static
	{
		// �A�C�e���o�^�p���X�g�ɒǉ��������A�C�e����˂�����
		ITEMS.add(ITEM_EXAMPLE);
		ITEMS.add(ITEM_EXAMPLE_TWO);
	}

	// MOD�ǂݍ��ݎ��̍ŏ��ɌĂ΂��Event
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// ������݂�Event�o�^���\�b�h
		// �����艺�̓o�^Event���g�����߂ɓo�^����
		MinecraftForge.EVENT_BUS.register(this);
	}

	// �A�C�e���o�^���ɌĂ΂��Event
	// �����Ŏ����I��Item�o�^���s��
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		// for�����g���ă��X�g����Item����o�^����
		for (Item item : ITEMS)
		{
			// �܂���Item�ɓo�^�p�̖��O��t����
			item.setRegistryName(new ResourceLocation(MOD_ID, item.getUnlocalizedName().substring(5)));
			// Registry��Item��o�^����
			event.getRegistry().register(item);
		}
	}

	// Item���̃��f����o�^����Event
	// �`��̓T�[�o�[�ł͍s�킸�N���C�A���g���݂̂̂��߁A@SideOnly(Side.CLIENT)�����Ă���
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		// for�����g���ă��X�g����Item����o�^����
		for (Item item : ITEMS)
		{
			// �ǂ̃A�C�e���ɂǂ̃��f�����Ή����邩��o�^����
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
