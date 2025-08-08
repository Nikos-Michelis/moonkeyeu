import ProgramsCard from "@/components/cards/ProgramsCard.jsx";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faClipboardList} from "@fortawesome/free-solid-svg-icons";

const RelatedPrograms = ({programs}) =>{
    const contentConfig = {
        styles: {
            wrapper: "article-card--small-wrapper",
            section: "launches-articles",
            card_type: "landscape-card__container--article-card"
        },
    };
    return(
        <section className="program-section">
            <div className="article__heading-box">
                <FontAwesomeIcon icon={faClipboardList} />
                <h2>Related Programs</h2>
            </div>
            <hr className="hr-100-sm bg-hr-600" />
            <div className="flex justify-center align-center padding-block-8">
                <div className="container"
                     data-type="full-width"
                     data-spacing="none"
                     data-scroll={programs.length > 2  ? "vertical" : undefined}>
                    <div className="margin-block-5 margin-inline-4">
                        <div className="grid-layout__landscape padding-block-2">
                            {programs?.length > 0 && (
                                programs.map((program) => (
                                    <ProgramsCard
                                        key={program.id}
                                        segment={`programs`}
                                        {...program}
                                        cardStyles={contentConfig?.styles}
                                    />
                                ))
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
export default RelatedPrograms;