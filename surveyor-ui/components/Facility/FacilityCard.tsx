import { FC, useState } from "react";
import { Facility } from "../../types/Facility";
import { PictureCard } from "../common/PictureCard";
import { AirportLounge } from "../../types/AirportLounge";
import { SurveyModal } from "../Survey/SurveyModal";
import useFetch from "../../hooks/useFetch";
import { Survey } from "../../types/Survey";

interface FacilityCardProps {
  facility: Facility;
  lounge: AirportLounge;
}

/**
 * Wrapper component around PictureCard for rendering facility data.
 * @param facility A facility object.
 * @param lounge A lounge object.
 * @returns A FacilityCard React component.
 */
export const FacilityCard: FC<FacilityCardProps> = ({ facility, lounge }) => {
  const [open, setOpen] = useState<boolean>(false);

  const {
    loading,
    data: survey,
    error,
  } = useFetch<Survey>(process.env.SURVEYOR_BACKEND_URL + "/surveys/1");

  return (
    <>
      <PictureCard
        title={facility.name}
        subtitle={lounge.name}
        image={facility.imageUrl ?? ""}
        content={facility.description}
        onClick={() => setOpen(true)}
      />
      <SurveyModal
        facility={facility}
        survey={survey}
        open={open}
        onClose={() => setOpen(false)}
        loading={loading}
      />
    </>
  );
};
