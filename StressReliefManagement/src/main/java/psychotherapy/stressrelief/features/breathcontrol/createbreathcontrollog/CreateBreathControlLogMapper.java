package psychotherapy.stressrelief.features.breathcontrol.createbreathcontrollog;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import psychotherapy.stressrelief.datacontext.model.BreathControl;

import java.util.UUID;

@Mapper
public interface CreateBreathControlLogMapper {
    CreateBreathControlLogMapper INSTANCE = Mappers.getMapper(CreateBreathControlLogMapper.class);
    public abstract CreateBreathControlLogResponse entityToResponse(BreathControl source);
    public abstract BreathControl requestToEntity(CreateBreathControlLogRequest source);
    @AfterMapping
    default void setStressReliefActionId(CreateBreathControlLogRequest source, @MappingTarget BreathControl dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}
