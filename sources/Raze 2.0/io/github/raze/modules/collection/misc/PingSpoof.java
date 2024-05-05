package io.github.raze.modules.collection.misc;

import io.github.raze.Raze;
import io.github.raze.events.collection.motion.EventMotion;
import io.github.raze.events.collection.network.EventPacketSend;
import io.github.raze.events.system.Event;
import io.github.nevalackin.radbus.Listen;
import io.github.raze.modules.system.AbstractModule;
import io.github.raze.modules.system.information.ModuleCategory;
import io.github.raze.settings.collection.NumberSetting;
import io.github.raze.utilities.collection.math.TimeUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;

import java.util.concurrent.CopyOnWriteArrayList;

public class PingSpoof extends AbstractModule {

    private final NumberSetting pingSpoofValueAverage;

    private final CopyOnWriteArrayList<Packet<?>> savedPacketList = new CopyOnWriteArrayList<>();
    private final TimeUtil timer;


    public PingSpoof() {
        super("PingSpoof", "Delays packets to simulate lag.", ModuleCategory.MISC);

        Raze.INSTANCE.managerRegistry.settingRegistry.add(

                pingSpoofValueAverage = new NumberSetting(this, "PingSpoof value", 1, 10000, 500)

        );

        timer = new TimeUtil();

    }

    @Listen
    public void onPacketSend(EventPacketSend event) {
        if(event.getPacket() instanceof C00PacketKeepAlive) {
            savedPacketList.add(event.getPacket());
            event.setCancelled(true);
        }
    }

    @Listen
    public void onMotion(EventMotion eventMotion) {
        if(eventMotion.getState() == Event.State.PRE){
            if (timer.elapsed(pingSpoofValueAverage.get().intValue(), true)) {
                for(Packet <?> packet : savedPacketList){
                    mc.thePlayer.sendQueue.addToSendQueue(packet);
                    savedPacketList.clear();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        for(Packet <?> packet : savedPacketList){
            mc.thePlayer.sendQueue.addToSendQueue(packet);
        }

        savedPacketList.clear();
    }

}