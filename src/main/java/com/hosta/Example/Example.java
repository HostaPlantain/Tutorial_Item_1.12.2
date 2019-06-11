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

	// このMODのID
	public static final String MOD_ID = "example";

	// このMOD専用のクリエイティブタブ
	public static final CreativeTabs TAB_EXAMPLE = new CreativeTabs(MOD_ID)
	{
		// タブに表示されるItemを指定する
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ITEM_EXAMPLE);
		}
	};

	// まとめて楽々登録用のリスト
	// 使わない方法もあるが使った方が楽
	private static final List<Item> ITEMS = new ArrayList<Item>();

	// 追加したいItemをここに
	// 必要に応じてItemを継承したクラスを作る
	public static final Item ITEM_EXAMPLE		= new Item().setUnlocalizedName("item_example").setCreativeTab(TAB_EXAMPLE);
	public static final Item ITEM_EXAMPLE_TWO	= new Item().setUnlocalizedName("item_example_two").setCreativeTab(TAB_EXAMPLE);

	static
	{
		// アイテム登録用リストに追加したいアイテムを突っ込む
		ITEMS.add(ITEM_EXAMPLE);
		ITEMS.add(ITEM_EXAMPLE_TWO);
	}

	// MOD読み込み時の最初に呼ばれるEvent
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// お馴染みのEvent登録メソッド
		// これより下の登録Eventを使うために登録する
		MinecraftForge.EVENT_BUS.register(this);
	}

	// アイテム登録時に呼ばれるEvent
	// ここで実質的なItem登録を行う
	@SubscribeEvent
	public void registerItems(Register<Item> event)
	{
		// for文を使ってリスト内のItem一つ一つを登録する
		for (Item item : ITEMS)
		{
			// まずはItemに登録用の名前を付ける
			item.setRegistryName(new ResourceLocation(MOD_ID, item.getUnlocalizedName().substring(5)));
			// RegistryにItemを登録する
			event.getRegistry().register(item);
		}
	}

	// Item等のモデルを登録するEvent
	// 描画はサーバーでは行わずクライアント側のみのため、@SideOnly(Side.CLIENT)をつけている
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		// for文を使ってリスト内のItem一つ一つを登録する
		for (Item item : ITEMS)
		{
			// どのアイテムにどのモデルが対応するかを登録する
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
