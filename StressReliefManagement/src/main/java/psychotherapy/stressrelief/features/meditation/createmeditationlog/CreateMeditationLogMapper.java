package psychotherapy.stressrelief.features.meditation.createmeditationlog;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import psychotherapy.stressrelief.datacontext.model.Meditation;

import java.util.UUID;

@Mapper
public interface CreateMeditationLogMapper {
    CreateMeditationLogMapper INSTANCE = Mappers.getMapper(CreateMeditationLogMapper.class);
    public abstract CreateMeditationLogResponse entityToResponse(Meditation source);
    public abstract Meditation requestToEntity(CreateMeditationLogRequest source);

    @AfterMapping
    default void setStressReliefActionId(CreateMeditationLogRequest source, @MappingTarget Meditation dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}