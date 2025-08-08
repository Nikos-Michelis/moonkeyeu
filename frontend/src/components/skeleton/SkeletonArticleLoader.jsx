import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';
function SkeletonArticleLoader(){
    return(
        <>
            <section className="article">
                <div className="container flex justify-center" data-type="wide" data-spacing="none">
                    <div className="container container--light-overlay article__content flex flex-column align-center" data-type="fixed" data-spacing="none">
                            <div className="container flex justify-start padding-block-start-7 padding-block-end-4">
                                <div className="skeleton skeleton--btn bg-dark-cosmos-300 opacity-6"></div>
                            </div>
                            <div className="container article__overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                <div className="article__image-box skeleton bg-dark-cosmos-300 opacity-6">
                                    <div className="skeleton--container skeleton--container--article">
                                        <div className="skeleton--container__icon skeleton-launch">
                                            <FontAwesomeIcon icon={faImage} />
                                        </div>
                                    </div>
                                </div>
                                <div className="container flex flex-column justify-center padding-2" data-type="full-bleed">
                                    <h1 className="skeleton skeleton--title bg-star-300 opacity-6"></h1>
                                    <div className="panel">
                                        <hr/>
                                        <div className="panel__wrapper">
                                            <div className="panel__container">
                                                <div className="skeleton skeleton--title bg-star-300 opacity-6"></div>
                                                <div className="skeleton skeleton--title bg-star-300 opacity-6"></div>
                                            </div>
                                            <div className="panel__container">
                                                <div className="skeleton skeleton--title bg-star-300 opacity-6"></div>
                                                <div className="skeleton skeleton--title bg-star-300 opacity-6"></div>
                                            </div>
                                        </div>
                                        <hr/>
                                    </div>
                                    <div className="skeleton skeleton--heading bg-star-300"></div>
                                </div>
                            </div>
                            <div className="article__info-container container skeleton--skeleton--container flex flex-column" data-type="full-bleed">
                                <section>
                                    <div className="article__heading-box skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></div>
                                    <hr className="hr-100-sm bg-hr-600" />
                                    <div className="article__info-box article__info-box--col">
                                        <p className="skeleton skeleton--title bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></p>
                                    </div>
                                </section>
                                <section>
                                    <div className="article__heading-box skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></div>
                                    <hr className="hr-100-sm bg-hr-600" />
                                    <div className="article__info-box">
                                        <p className="skeleton skeleton--title bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></p>
                                    </div>
                                </section>
                                <section>
                                    <div className="article__heading-box skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></div>
                                    <hr className="hr-100-sm bg-hr-600" />
                                    <div className="article__info-box">
                                        <p className="skeleton skeleton--title bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--heading bg-dark-cosmos-300 opacity-6"></p>
                                        <p className="skeleton skeleton--text bg-dark-cosmos-300 opacity-6"></p>
                                    </div>
                                </section>
                            </div>
                        </div>
                </div>
            </section>
        </>
    )
}
export default SkeletonArticleLoader;
