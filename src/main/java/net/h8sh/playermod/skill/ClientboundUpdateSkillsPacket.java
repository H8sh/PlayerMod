package net.h8sh.playermod.skill;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;

public class ClientboundUpdateSkillsPacket implements Packet<ClientGamePacketListener> {
    private final boolean reset;
    private final Map<ResourceLocation, Skill.Builder> added;
    private final Set<ResourceLocation> removed;
    private final Map<ResourceLocation, SkillProgress> progress;

    public ClientboundUpdateSkillsPacket(boolean pReset, Collection<Skill> pAdded, Set<ResourceLocation> pRemoved, Map<ResourceLocation, SkillProgress> pProgress) {
        this.reset = pReset;
        ImmutableMap.Builder<ResourceLocation, Skill.Builder> builder = ImmutableMap.builder();

        for(Skill skill : pAdded) {
            builder.put(skill.getId(), skill.deconstruct());
        }

        this.added = builder.build();
        this.removed = ImmutableSet.copyOf(pRemoved);
        this.progress = ImmutableMap.copyOf(pProgress);
    }

    public ClientboundUpdateSkillsPacket(FriendlyByteBuf pBuffer) {
        this.reset = pBuffer.readBoolean();
        this.added = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, Skill.Builder::fromNetwork);
        this.removed = pBuffer.readCollection(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::readResourceLocation);
        this.progress = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, SkillProgress::fromNetwork);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBoolean(this.reset);
        pBuffer.writeMap(this.added, FriendlyByteBuf::writeResourceLocation, (p_179441_, p_179442_) -> {
            p_179442_.serializeToNetwork(p_179441_);
        });
        pBuffer.writeCollection(this.removed, FriendlyByteBuf::writeResourceLocation);
        pBuffer.writeMap(this.progress, FriendlyByteBuf::writeResourceLocation, (p_179444_, p_179445_) -> {
            p_179445_.serializeToNetwork(p_179444_);
        });
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void handle(ClientGamePacketListener pHandler) {
        pHandler.handleUpdateAdvancementsPacket(this);
    }

    public Map<ResourceLocation, Skill.Builder> getAdded() {
        return this.added;
    }

    public Set<ResourceLocation> getRemoved() {
        return this.removed;
    }

    public Map<ResourceLocation, SkillProgress> getProgress() {
        return this.progress;
    }

    public boolean shouldReset() {
        return this.reset;
    }
}