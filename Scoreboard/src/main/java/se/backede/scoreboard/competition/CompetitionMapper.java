/*
 */
package se.backede.scoreboard.competition;

import se.backede.scoreboard.common.AbstractMapper;

/**
 *
 * @author Joakim Backede <joakim.backede@outlook.com>
 */
public class CompetitionMapper extends AbstractMapper<CompetitionDto, CompetitionEntity> {

    @Override
    public CompetitionEntity mapToEntity(CompetitionDto dto) {
        return CompetitionEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .competitionDate(dto.getCompetitionDate())
                .build();
    }

    @Override
    public CompetitionDto mapToDto(CompetitionEntity entity) {
        return CompetitionDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .competitionDate(entity.getCompetitionDate())
                .build();
    }

}
