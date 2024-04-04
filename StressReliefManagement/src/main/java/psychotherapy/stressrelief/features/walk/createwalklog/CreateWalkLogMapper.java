package psychotherapy.stressrelief.features.walk.createwalklog;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import psychotherapy.stressrelief.datacontext.model.Walk;

import java.util.UUID;

@Mapper
public interface CreateWalkLogMapper {
    CreateWalkLogMapper INSTANCE = Mappers.getMapper(CreateWalkLogMapper.class);
    public abstract CreateWalkLogResponse entityToResponse(Walk source);
    public abstract Walk requestToEntity(CreateWalkLogRequest source);
    @AfterMapping
    default void setStressReliefActionId(CreateWalkLogRequest source, @MappingTarget Walk dest) {
        dest.setStressReliefActionId(UUID.randomUUID().toString());
    }
}
