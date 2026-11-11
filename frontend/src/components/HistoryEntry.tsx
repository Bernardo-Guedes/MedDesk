import { Link } from "react-router";
import StatusAtendimento, {
  type TipoStatusAtendimento,
} from "@/components/ui/StatusAtendimento";

interface HistoryEntryProps {
  title: string;
  status?: string;
  statusAtendimento?: TipoStatusAtendimento;
  details: string[];
  to?: string;
}

function HistoryEntry({
  title,
  status,
  statusAtendimento,
  details,
  to,
}: HistoryEntryProps) {
  const content = (
    <>
      <span className="font-semibold text-slate-800">{title}</span>
      <div className="flex flex-wrap gap-2">
        {status && <StatusAtendimento texto={status} status={statusAtendimento} />}
        {details.map((detail) => (
          <StatusAtendimento key={detail} texto={detail} />
        ))}
      </div>
    </>
  );

  const className =
    "flex flex-col gap-3 rounded-xl bg-white p-5 shadow-sm transition-shadow hover:shadow-md sm:flex-row sm:items-center sm:justify-between";

  return to ? (
    <Link to={to} className={className}>
      {content}
    </Link>
  ) : (
    <article className={className}>{content}</article>
  );
}

export default HistoryEntry;
