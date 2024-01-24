package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.telemetry.WorldSessionTelemetryManager;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientSkill {
    private final SkillList advancements = new SkillList();
    private final Map<Skill, AdvancementProgress> progress = Maps.newHashMap();
    @Nullable
    private ClientSkill.Listener listener;
    @Nullable
    private Skill selectedTab;

    public void update(ClientboundUpdateAdvancementsPacket pPacket) {

        //TODO: add skills here

    }

    public void setSelectedTab(@Nullable Skill skill) {
        if (this.selectedTab != skill) {
            this.selectedTab = skill;
            if (this.listener != null) {
                this.listener.onSelectedTabChanged(skill);
            }
        }

    }

    public void setListener(@Nullable ClientSkill.Listener pListener) {
        this.listener = pListener;
        this.advancements.setListener(pListener);
        if (pListener != null) {
            for (Map.Entry<Skill, AdvancementProgress> entry : this.progress.entrySet()) {
                pListener.onUpdateAdvancementProgress(entry.getKey(), entry.getValue());
            }

            pListener.onSelectedTabChanged(this.selectedTab);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public interface Listener extends SkillList.Listener {
        void onUpdateAdvancementProgress(Skill pAdvancement, AdvancementProgress pProgress);

        void onSelectedTabChanged(@Nullable Skill pAdvancement);
    }
}