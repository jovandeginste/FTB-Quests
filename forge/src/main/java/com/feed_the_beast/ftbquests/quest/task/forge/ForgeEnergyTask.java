package com.feed_the_beast.ftbquests.quest.task.forge;

import com.feed_the_beast.ftbquests.FTBQuests;
import com.feed_the_beast.ftbquests.quest.PlayerData;
import com.feed_the_beast.ftbquests.quest.Quest;
import com.feed_the_beast.ftbquests.quest.task.EnergyTask;
import com.feed_the_beast.ftbquests.quest.task.TaskData;
import com.feed_the_beast.ftbquests.quest.task.TaskType;
import com.feed_the_beast.ftbquests.quest.task.TaskTypes;
import com.feed_the_beast.mods.ftbguilibrary.utils.StringUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author LatvianModder
 */
public class ForgeEnergyTask extends EnergyTask
{
	public static final ResourceLocation EMPTY_TEXTURE = new ResourceLocation(FTBQuests.MOD_ID, "textures/tasks/fe_empty.png");
	public static final ResourceLocation FULL_TEXTURE = new ResourceLocation(FTBQuests.MOD_ID, "textures/tasks/fe_full.png");

	public ForgeEnergyTask(Quest quest)
	{
		super(quest);
	}

	@Override
	public TaskType getType()
	{
		return TaskTypes.FORGE_ENERGY;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public MutableComponent getAltTitle()
	{
		return new TranslatableComponent("ftbquests.task.ftbquests.forge_energy.text", StringUtils.formatDouble(value, true));
	}

	@Override
	public TaskData createData(PlayerData data)
	{
		return new Data(this, data);
	}

	public static class Data extends TaskData<ForgeEnergyTask>
	{
		private Data(ForgeEnergyTask task, PlayerData data)
		{
			super(task, data);
		}

		public int receiveEnergy(int maxReceive, boolean simulate)
		{
			if (maxReceive > 0 && !isComplete())
			{
				long add = Math.min(maxReceive, task.value - progress);

				if (task.maxInput > 0)
				{
					add = Math.min(add, task.maxInput);
				}

				if (add > 0L)
				{
					if (!simulate)
					{
						addProgress(add);
					}

					return (int) add;
				}
			}

			return 0;
		}
	}
}