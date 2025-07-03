import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faImage } from '@fortawesome/free-regular-svg-icons';

function SkeletonArticleLoader(){
    return(
        <>
            <section className="article-section">
                <div className="container __article flex justify-center" data-type="wide" data-spacing="none">
                    <div className="container __article overlay flex flex-column align-center" data-type="fixed" data-spacing="none">
                            <div className="container flex justify-start padding-block-start-7 padding-block-end-2" data-type="full-bleed">
                                <div className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-btn"></div>
                            </div>
                            <div className="container __overview flex flex-column justify-center align-center bg-dark-cosmos-300" data-type="full-bleed">
                                <div className="skeleton skeleton-img">
                                    <div className="article-img">
                                        <div className="img-icon-box skeleton-launch">
                                            <FontAwesomeIcon icon={faImage} />
                                        </div>
                                    </div>
                                </div>
                                <div className="overview-info-wrapper container flex flex-column justify-center padding-2" data-type="full-bleed">
                                    <div className="article-detail-container">
                                        <h1 className="article-detail skeleton bg-star-300 opacity-6 skeleton-title"></h1>
                                        <div className="panel-body">
                                            <hr className="hr-7-xs"/>
                                            <div className="panel-info-wrapper">
                                                <div className="panel-info-container">
                                                    <div className="skeleton bg-star-300 opacity-6 skeleton-title"></div>
                                                    <div className="skeleton bg-star-300 opacity-6 skeleton-title"></div>
                                                </div>
                                                <div className="panel-info-container">
                                                    <div className="skeleton bg-star-300 opacity-6 skeleton-title"></div>
                                                    <div className="skeleton bg-star-300 opacity-6 skeleton-title"></div>
                                                </div>
                                            </div>
                                            <hr className="hr-7-xs"/>
                                        </div>
                                        <div className="skeleton bg-star-300 skeleton-heading"></div>
                                    </div>
                                </div>
                            </div>
                            <div className="article-info-container container flex flex-column" data-type="full-bleed">
                                <section>
                                    <div className="heading-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></div>
                                    <hr className="hr-7-sm bg-hr-600" />
                                    <div className="info-box">
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-title"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></p>
                                    </div>
                                    <div className="img-box"></div>
                                </section>
                                <section>
                                    <div className="heading-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></div>
                                    <hr className="hr-7-sm bg-hr-600" />
                                    <div className="info-box">
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-title"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></p>
                                    </div>
                                </section>
                                <section>
                                    <div className="heading-box skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></div>
                                    <hr className="hr-7-sm bg-hr-600" />
                                    <div className="info-box">
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-title"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-heading"></p>
                                        <p className="skeleton bg-dark-cosmos-300 opacity-6 skeleton-text"></p>
                                    </div>
                                    <div className="img-box"></div>
                                </section>
                            </div>
                        </div>
                </div>
            </section>
        </>
    )
}
export default SkeletonArticleLoader;
