import ProgramsCard from "@/components/cards/ProgramsCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBuilding } from '@fortawesome/free-solid-svg-icons';

const RelatedPrograms = ({programs}) =>{
    return(
        <section className="program-section">
            <div className="heading-box">
                <FontAwesomeIcon icon={faBuilding} />
                <h2>Related Programs</h2>
            </div>
            <hr className="hr-7-sm bg-hr-600" />
            <div className="container flex flex-wrap justify-center align-center padding-block-8" data-type="full-bleed" data-spacing="none">
                <div className="flex justify-center">
                    <div className={`container flex justify-center ${programs.length > 2 ? "scrolling" : ""}`} data-spacing="none">
                        <div className="margin-block-5 margin-inline-4">
                            <div className="landscape-grid padding-block-2">
                                {programs?.length > 0 && (
                                    programs.map((program) => (
                                        <ProgramsCard
                                            key={program.id}
                                            segment={`programs`}
                                            {...program}
                                        />
                                    ))
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RelatedPrograms;