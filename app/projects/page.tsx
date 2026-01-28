import ProjectsClient from './ProjectsClient';
import { fetchRepositories } from '@/lib/github';

export default async function ProjectsPage() {
  const repos = await fetchRepositories();
  return <ProjectsClient repos={repos} />;
}
